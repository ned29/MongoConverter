package com.converter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ConvertToMongoQuery {

    /**
     * Convert sql values into mongodb query
     *
     * @param query sql query
     * @return values needed for mongo db query
     */
    List<String> processingSql(String query);
}