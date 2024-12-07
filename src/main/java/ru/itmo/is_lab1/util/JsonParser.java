package ru.itmo.is_lab1.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.itmo.is_lab1.exceptions.util.JsonParserException;
import ru.itmo.is_lab1.rest.dto.MusicBandDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonParser {
    public List<MusicBandDTO> parseList(byte[] fileBytes) throws JsonParserException {
        Gson gson = new Gson();
        try(var bis = new ByteArrayInputStream(fileBytes)) {
            try (var reader = new InputStreamReader(bis)){
                Type personListType = new TypeToken<List<MusicBandDTO>>(){}.getType();
                return gson.fromJson(reader, personListType);
            }
        } catch (IOException e) {
            throw new JsonParserException(e.getMessage());
        }
    }
}
