package com.mongoHandler;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MongoDBHandler {

    /**
     * This method used for the insert the document into collection
     *
     * @param collectionName
     * @param input
     *            json String
     * @return
     */
    boolean insertDocument(String collectionName, String input);

    /**
     * This method is used for the retrieve the documents based on the condition
     *
     * @param collectionName
     * @param condition
     *            string json
     * @return
     */
    List<String> find(String collectionName, String condition);
}