package com.converter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Getter
@Setter
public class ConvertToMongo {
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
    private String sqlQuery;

    private String regExp(String startWord, String finishWord) {
        Pattern pattern = Pattern.compile(PATTERN_START + startWord + PATTERN_BETWEEN + finishWord + PATTERN_FINISH);
        Matcher matcher = pattern.matcher(sqlQuery);
        return matcher.find() ? trim(matcher.group()) : null;
    }

    private String trim(String query) {
        return query.replaceAll("\\[", "").replaceAll("\\]", "").trim();
    }

    public void splitSqlQuery() {
        select = regExp(SELECT, FROM);
        from = regExp(FROM, WHERE);
        where = regExp(WHERE, ORDER_BY);
        orderBy = regExp(ORDER_BY, ASC_DESC);
        skip = regExp(SKIP, LIMIT);
        limit = regExp(LIMIT, BRACKET);
    }

//    public String createMongoQuery() {
//    }
}
