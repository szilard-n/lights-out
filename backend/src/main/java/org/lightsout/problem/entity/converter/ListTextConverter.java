package org.lightsout.problem.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Collections;
import java.util.List;

@Converter
public class ListTextConverter implements AttributeConverter<List<List<Byte>>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<List<Byte>> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting int[][] to JSON", e);
        }
    }

    @Override
    public List<List<Byte>> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(dbData,new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to int[][]", e);
        }
    }
}
