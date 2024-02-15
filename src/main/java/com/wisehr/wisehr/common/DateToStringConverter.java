package com.wisehr.wisehr.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DateToStringConverter implements AttributeConverter<String, String>{

    private static final Logger logger = LoggerFactory.getLogger(DateToStringConverter.class);    // logger

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        StringBuilder sb = new StringBuilder();
        if(dbData!=null) {
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSSS");
            Date date = null;
            try {
                date = dbFormat.parse(dbData);
                SimpleDateFormat entityFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                sb.append(entityFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
                logger.error("Error Occured during convert \"_dt\" value to entity attribute format.");
            }
            return sb.toString();
        }
        return dbData;
    }

}
