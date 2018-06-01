package com.mongoHandler;


import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class MongoDBHandlerImpl implements MongoDBHandler {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MongoDBHandlerImpl.class);

    private MongoClient mongoClient;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.database}")
    private String dataBaseName;

    @PostConstruct
    public void init() {
        mongoClient = new MongoClient(host, port);
    }

    @Override
    public boolean insertDocument(String dataBaseName, String collectionName, String input) {
        try {
            MongoDatabase db = mongoClient.getDatabase(dataBaseName);
            MongoCollection<Document> collection = db.getCollection(collectionName);
            final Document dbObjectInput = Document.parse(input);
            collection.insertOne(dbObjectInput);
            return true;
        } catch (MongoWriteException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<String> find(String dataBaseName, String collectionName, String condition) {
        List<String> result = new ArrayList<>();
        try {
            MongoDatabase db = mongoClient.getDatabase(dataBaseName);
            MongoCollection<Document> collection = db.getCollection(collectionName);
            collection.find(BasicDBObject.parse(condition)).forEach((Block<Document>) document -> result.add(JSON.serialize(document)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
}