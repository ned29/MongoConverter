package com.parser;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface SqlParser {

    /**
     * Parsing sql query into map
     * @param sqlQuery sql query
     * @return values for selectors
     */
    Map<String, String> parseSql(String sqlQuery);

    /**
     * Finding special word in string
     * @param word word which we are finding
     * @return result of finding
     */
    boolean regExpForWords(String word);
}