package com.mongoHandler;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MongoDBHandler {

    /**
     * Insert collections to mongodb
     *
     * @param collectionName
     * @param input
     * @return boolean value with result of inserting
     */
    boolean insertDocument(String collectionName, String input);

    /**
     * Find collections in mongodb
     *
     * @param collectionName
     * @param condition
     * @return list of results
     */
    List<String> find(String collectionName, String condition);
}