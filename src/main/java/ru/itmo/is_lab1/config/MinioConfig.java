package ru.itmo.is_lab1.config;

import io.minio.MinioClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

@ApplicationScoped
@Getter
public class MinioConfig {
    private final String bucket;

    private final String url;

    private final String accessKey;

    private final String secretKey;

    public MinioConfig() throws IOException {
        Properties properties = new Properties();
        properties.load(MinioConfig.class.getClassLoader().getResourceAsStream("/minio.cfg"));

        bucket = properties.getProperty(MinioPropsNames.BUCKET.getName());
        url = properties.getProperty(MinioPropsNames.URL.getName());
        accessKey = properties.getProperty(MinioPropsNames.ACCESS_KEY.getName());
        secretKey = properties.getProperty(MinioPropsNames.SECRET_KEY.getName());
    }

    @Produces
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Getter
    public enum MinioPropsNames{
        BUCKET("bucket"),
        URL("url"),
        ACCESS_KEY("accessKey"),
        SECRET_KEY("secretKey");
        private final String name;

        MinioPropsNames(String name) {
            this.name = name;
        }
    }
}
