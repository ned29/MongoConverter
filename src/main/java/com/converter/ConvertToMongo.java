package com.converter;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConvertToMongo {
    private static final String PATTERN_START = "(?<=";
    private static final String PATTERN_BETWEEN = ")(.*\\n?)(?=";
    private static final String PATTERN_FINISH = ")";

    private String regExp(String startWord, String finishWord, String sqlQuery) {
        Matcher matcher = Pattern.compile(PATTERN_START + startWord + PATTERN_BETWEEN + finishWord + PATTERN_FINISH).matcher(sqlQuery);
        return matcher.find() ? matcher.toString() : null;
    }

    public String splitSqlQuery(String sqlQuery) {
        return sqlQuery;
    }
}
