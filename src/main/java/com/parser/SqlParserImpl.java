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

/**
 * Parsing sql query
 */
@Component
public class SqlParserImpl implements SqlParser {

    private static final String PATTERN_START = "(?<=";
    private static final String PATTERN_BETWEEN = ")(.*\\n?)(?=";
    private static final String PATTERN_FINISH = ")";
    private static final String SELECT = "select";
    private static final String FROM = "from";
    private static final String WHERE = "where";
    private static final String ORDER_BY = "order by";
    private static final String SKIP = "skip";
    private static final String LIMIT = "limit";
    private static final String BRACKET = "]";

    @Getter
    @Setter
    private String sqlQuery;

    /**
     * Finding value between words
     *
     * @param startWord
     * @param finishWord
     * @return value
     */
    private String regExp(String startWord, String finishWord) {
        Pattern pattern = Pattern.compile(PATTERN_START + startWord + PATTERN_BETWEEN + finishWord + PATTERN_FINISH);
        Matcher matcher = pattern.matcher(sqlQuery);
        return matcher.find() ? trim(matcher.group()) : null;
    }

    /**
     * Trim values
     *
     * @param query
     * @return
     */
    private String trim(String query) {
        return query.replaceAll("\\[", "").replaceAll("\\]", "").trim();
    }

    /**
     * @return
     */
    @Override
    public Map<String, String> invokeParser(String sqlQuery) {
        this.sqlQuery = sqlQuery;
        return numberOfKeyWord();
    }

    /**
     * RegExp for finding words in query
     *
     * @param word keywords
     * @return true or false
     */
    private boolean regExpForWords(String word) {
        Pattern pattern = Pattern.compile("\\b(?:" + word + ")\\b");
        Matcher matcher = pattern.matcher(sqlQuery);
        return matcher.find();
    }

    /**
     * Finding used keywords
     *
     * @return used keywords
     */
    private Map<String, String> numberOfKeyWord() {
        List<String> words = Arrays.asList(SELECT, FROM, WHERE, ORDER_BY, SKIP, LIMIT);
        return findValues(words.stream().filter(this::regExpForWords).collect(Collectors.toList()));
    }

    /**
     * Finding values
     *
     * @param words list of words
     * @return map with keywords and value
     */
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