package com.mongoHandler;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MongoDBHandler {

    /**
     * Insert collections to mongodb
     *
     * @param dataBaseName
     * @param collectionName
     * @param input
     * @return
     */
    boolean insertDocument(String dataBaseName, String collectionName, String input);

    /**
     * Find collections in mongodb
     *
     * @param dataBaseName
     * @param collectionName
     * @param condition
     * @return
     */
    List<String> find(String dataBaseName, String collectionName, String condition);
}