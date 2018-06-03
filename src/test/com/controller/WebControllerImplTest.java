package com.controller;

import com.converter.ConvertToMongoQuery;
import com.mongoHandler.MongoDBHandlerImpl;
import com.mongodb.MongoClient;
import com.parser.SqlParser;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerImplTest {
    private static final String DATA_BASE_NAME = "test";
    private static final String COLLECTION_NAME = "test";
    private static final String COLLECTION_PATH = "src\\test\\resources\\testCollection.json";
    private static final String COLLECTION_PATH_2 = "src\\test\\resources\\testCollection3.json";
    private static final String COLLECTION_PATH_3 = "src\\test\\resources\\testCollection2.json";
    private static final String SQL_QUERY_ALL = "SELECT [*] [FROM test]";
    private static final String SQL_QUERY = "SELECT [name.first] [FROM test] [WHERE awards.award='W.W. McDowell Award' OR awards.award='Turing Award']";
    private static String collection;
    private static String collection2;
    private static String collection3;
    private static MongodForTestsFactory testsFactory;
    private static MongoClient mongoClient = null;

    @Autowired
    private ConvertToMongoQuery convert;

    @Autowired
    private SqlParser parser;

    @Autowired
    private MongoDBHandlerImpl handler;

    @BeforeClass
    public static void init() throws Exception {
        setUpEmbeddedMongo();
    }

    @PostConstruct
    public void initMocks() {
        handler.setMongoClient(mongoClient);
        handler.insertDocument(COLLECTION_NAME, collection);
        handler.insertDocument(COLLECTION_NAME, collection2);
        handler.insertDocument(COLLECTION_NAME, collection3);
    }

    private static void setUpEmbeddedMongo() throws IOException {
        testsFactory = MongodForTestsFactory.with(Version.LATEST_NIGHTLY);
        mongoClient = testsFactory.newMongo();
        String port = "" + mongoClient.getAddress().getPort();
        System.setProperty("spring.data.mongodb.port", port);
        System.setProperty("spring.data.mongodb.database:", DATA_BASE_NAME);
        collection = FileUtils.readFileToString(new File(COLLECTION_PATH), "UTF-8");
        collection2 = FileUtils.readFileToString(new File(COLLECTION_PATH_2), "UTF-8");
        collection3 = FileUtils.readFileToString(new File(COLLECTION_PATH_3), "UTF-8");
    }

    @Test
    public void testConverter() {
        List<String> resultForAll = convert.processingSql(SQL_QUERY_ALL);
        List<String> resultWithWhere = convert.processingSql(SQL_QUERY);
        List<String> resultWithLimit = convert.processingSql(SQL_QUERY + "[LIMIT 1]");
        List<String> resultWithSkip = convert.processingSql(SQL_QUERY + "[SKIP 1]");
        assertEquals(resultForAll.size(), 3);
        assertEquals(resultWithWhere.size(), 2);
        assertEquals(resultWithLimit.size(), 1);
        assertEquals(resultWithSkip.size(), 1);
    }

    @AfterClass
    public static void close() {
        mongoClient.close();
        testsFactory.shutdown();
    }
}