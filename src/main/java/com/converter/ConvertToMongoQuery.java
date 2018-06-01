package com.converter;

import org.springframework.stereotype.Component;

@Component
public interface ConvertToMongoQuery {

    /**
     * Convert sql values into mongodb query
     *
     * @param query sql query
     * @return mongodb query
     */
    String processingSql(String query);
}