package com.mongoHandler;

import com.mongodb.BasicDBObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MongoDBHandler {

    /**
     * This method used for the insert the document into collection
     *
     * @param collectionName db collection name
     * @param input          json String
     * @return result of inserting
     */
    boolean insertDocument(String collectionName, String input);

    /**
     * This method is used for the retrieve the documents based on the condition
     *
     * @param collectionName db collection name
     * @param condition      string json
     * @return list of searched elements
     */
    List<String> find(String collectionName, String condition, BasicDBObject select, String sort, int limit, int skip);
}