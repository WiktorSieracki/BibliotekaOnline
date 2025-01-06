package com.example.bibliotekaonline.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
public class YearStringConverter implements AttributeConverter<Year, String> {

    @Override
    public String convertToDatabaseColumn(Year year) {
        return year != null ? year.toString() : null;
    }

    @Override
    public Year convertToEntityAttribute(String dbData) {
        return dbData != null ? Year.parse(dbData) : null;
    }
}
