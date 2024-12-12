package ru.itmo.is_lab1.service.impl;

import io.minio.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.io.IOUtils;
import ru.itmo.is_lab1.config.MinioConfig;
import ru.itmo.is_lab1.exceptions.service.CanNotLoadFileException;
import ru.itmo.is_lab1.exceptions.service.CanNotSaveFileException;
import ru.itmo.is_lab1.rest.dto.FileDTO;
import ru.itmo.is_lab1.service.FileStorageService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

@ApplicationScoped
public class FileStorageServiceImpl implements FileStorageService {
    @Inject
    private MinioClient minioClient;

    @Inject
    private MinioConfig minioConfig;

    @Override
    public String saveFile(FileDTO file) throws CanNotSaveFileException {
        if (file.getBytes() == null || file.getName() == null) {
            throw new CanNotSaveFileException("Ошибка валидации файла!");
        }

        file.setName(makeFileName(file.getName()));

        createBucketIfNotExist(minioConfig.getBucket());

        if (!upload(file)) throw new CanNotSaveFileException("Ошибка сохранения файла!");

        return file.getName();
    }

    @Override
    public FileDTO getFile(String fileName) throws CanNotLoadFileException {
        if (fileName == null) throw new CanNotLoadFileException("Ошибка валидации имени файла!");

        var res = loadFile(fileName);

        try(res){
            byte[] bytes = IOUtils.toByteArray(res);
            return new FileDTO(fileName, bytes);
        } catch (Exception e){
            throw new CanNotLoadFileException("Ошибка загрузки файла из базы!");
        }
    }

    private GetObjectResponse loadFile(String fileName) throws CanNotLoadFileException {

        try{
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioConfig.getBucket())
                            .object(fileName)
                            .build()
            );
        } catch (Exception e){
            throw new CanNotLoadFileException("Ошибка загрузки файла из базы!");
        }
    }

    private String makeFileName(String currentName){
        return currentName + UUID.randomUUID();
    }

    private boolean upload(FileDTO file){
        var inputStream = new ByteArrayInputStream(file.getBytes());

        try{
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucket())
                            .stream(inputStream, inputStream.available(), -1)
                            .object(file.getName())
                            .build()
            );
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private void createBucketIfNotExist(String bucketName) throws CanNotSaveFileException {
        try{
            boolean existRes = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
            if (existRes) return;

            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (Exception e){
            throw new CanNotSaveFileException("Ошибка выгрузки файла в базу!");
        }
    }
}
