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
    private static final String PATTERN_START = "(?<=";
    private static final String PATTERN_BETWEEN = ")(.*\\n?)(?=";
    private static final String PATTERN_FINISH = ")";
    private static final String BRACKET = "]";

    @Getter
    @Setter
    private String sqlQuery;

    @Override
    public Map<String, String> parseSql(String sqlQuery) {
        this.sqlQuery = sqlQuery;
        List<String> words = Arrays.asList(SELECT.name(), FROM.name(), WHERE.name(), ORDER_BY.name(), SKIP.name(), LIMIT.name());
        return findValues(words.stream().filter(this::regExpForWords).collect(Collectors.toList()));
    }

    private String regExp(String startWord, String finishWord) {
        Pattern pattern = Pattern.compile(PATTERN_START + startWord + PATTERN_BETWEEN + finishWord + PATTERN_FINISH);
        Matcher matcher = pattern.matcher(sqlQuery);
        return matcher.find() ? trim(matcher.group()) : null;
    }

    private String trim(String query) {
        return query.replaceAll("\\[", "").replaceAll("\\]", "").trim();
    }

    private boolean regExpForWords(String word) {
        Pattern pattern = Pattern.compile("\\b(?:" + word + ")\\b");
        Matcher matcher = pattern.matcher(sqlQuery);
        return matcher.find();
    }

    private Map<String, String> findValues(List<String> words) {
        Map<String, String> keyValue = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            if (i == words.size() - 1) {
                keyValue.put(words.get(i), regExp(words.get(i), BRACKET));
            } else {
                keyValue.put(words.get(i), regExp(words.get(i), words.get(i + 1)));
            }
        }
        return keyValue;
    }
}