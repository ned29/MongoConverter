package com.mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MongodbHandler {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MongodbHandler.class);

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

}
