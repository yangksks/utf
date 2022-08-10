package com.ssafy.utf.db.entity.statistics;

import com.google.gson.Gson;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;

public class UnderstandConverter implements AttributeConverter<ArrayList<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(ArrayList<Integer> attribute) {
        String json = new Gson().toJson(attribute);
        return json;
    }

    @Override
    public ArrayList<Integer> convertToEntityAttribute(String dbData) {
        System.out.println(dbData);
        return null;
    }
}
