package com.parser;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.parser.SqlNames.*;

/**
 * Parsing sql query
 */
@Component
public class SqlParserImpl implements SqlParser {
    private static final String REGEXP_FOR_VALUES_IN__BRACKETS = "\\[([^]]+)\\]";

    @Getter
    @Setter
    private String sqlQuery;

    @Override
    public Map<String, String> parseSql(String sqlQuery) {
        this.sqlQuery = sqlQuery.replaceAll("\\[asc\\]", ":1").replaceAll("\\[desc\\]", ":-1");
        List<String> words = Arrays.asList(SELECT.getValue(), FROM.getValue(), WHERE.getValue(), ORDER_BY.getValue(), SKIP.getValue(), LIMIT.getValue());
        return findValues(words.stream().filter(this::regExpForWords).collect(Collectors.toList()));
    }

    private boolean regExpForWords(String word) {
        Pattern pattern = Pattern.compile("\\b(?:" + word + ")\\b");
        Matcher matcher = pattern.matcher(sqlQuery);
        return matcher.find();
    }

    private Map<String, String> findValues(List<String> words) {
        int count = 0;
        Map<String, String> keyValue = new HashMap<>();
        Pattern pattern = Pattern.compile(REGEXP_FOR_VALUES_IN__BRACKETS);
        Matcher matcher = pattern.matcher(sqlQuery);
        while (matcher.find()) {
            String value = matcher.group().replaceAll(words.get(count), "").
                    replaceAll("\\[", "").replaceAll("\\]", "").trim();
            keyValue.put(words.get(count), value);
            count++;
        }
        return keyValue;
    }
}