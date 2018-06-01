package com.parser;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class SqlParser {
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
    private static final String ASC_DESC = "asc|desc";
    private String select;
    private String from;
    private String where;
    private String orderBy;
    private String skip;
    private String limit;

    @Getter
    @Setter
    private String sqlQuery;

    //    public void setSqlQuery(String sqlQuery) {
//        this.sqlQuery = sqlQuery.replaceAll(">", "\\$gt").replaceAll("<", "\\$lt").replaceAll("<=", "\\$lte").
//                replaceAll(">=", "\\$gte").replaceAll("<>", "\\$ne").replaceAll("!=", "\\$ne").
//                replaceAll("=", ":").replaceAll("]", "};").replaceAll("\\[", "{").
//                replace(FROM, FROM + ":").replace(WHERE, WHERE + ":").replace(ORDER_BY, "sort:").
//                replace(SKIP, SKIP + ":").replace(LIMIT, LIMIT + ":");
//    }
//
    private String regExp(String startWord, String finishWord) {
        Pattern pattern = Pattern.compile(PATTERN_START + startWord + PATTERN_BETWEEN + finishWord + PATTERN_FINISH);
        Matcher matcher = pattern.matcher(sqlQuery);
        return matcher.find() ? trim(matcher.group()) : null;
    }

    private String trim(String query) {
        return query.replaceAll("\\[", "").replaceAll("\\]", "").trim();
    }

    public void invokeParser() {
        Map<String, String> result = new HashMap<>();
        result = numberOfKeyWord();
    }

    private String replacementForWhere(String replace) {
        return replace != null ? replace.replaceAll(">", "\\$gt").replaceAll("<", "\\$lt").replaceAll("<=", "\\$lte").
                replaceAll(">=", "\\$gte").replaceAll("<>", "\\$ne").replaceAll("!=", "\\$ne").
                replaceAll("=", ":") : null;
    }

    private String replacementForOrderBy(String replace) {
        return replace != null ? replace.replaceAll("asc", "1").replaceAll("desc", "-1") : null;
    }

    private boolean regExpForWords(String word) {
        Pattern pattern = Pattern.compile("\\b(?:" + word + ")\\b");
        Matcher matcher = pattern.matcher(sqlQuery);
        return matcher.find();
    }

    private Map<String, String> numberOfKeyWord() {
        List<String> words = Arrays.asList(SELECT, FROM, WHERE, ORDER_BY, SKIP, LIMIT);
        return findValues(words.stream().filter(this::regExpForWords).collect(Collectors.toList()));
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
