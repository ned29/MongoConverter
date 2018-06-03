package com.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Sql;
import com.mongoHandler.MongoDBHandler;
import com.mongodb.BasicDBObject;
import com.parser.SqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class ConvertToMongoQueryImpl implements ConvertToMongoQuery {
    private static final String AND = "and";
    private static final String OR = "or";
    private Sql sql;

    @Autowired
    private SqlParser sqlParser;

    @Autowired
    private MongoDBHandler mongoDBHandler;

    @Override
    public String processingSql(String query) {
        sql = new ObjectMapper().convertValue(sqlParser.parseSql(query), Sql.class);
        return mongoDBHandler.find(sql.getFrom(), processWhere(), processSelect(), processOrderBy(), processLimit(), processSkip());
    }

    private int processSkip() {
        return sql.getSkip() != null ? Integer.valueOf(sql.getSkip()) : 0;
    }

    private int processLimit() {
        return sql.getLimit() != null ? Integer.valueOf(sql.getLimit()) : 0;
    }

    private String processOrderBy() {
        if (sql.getOrderBy() != null) {
            String[] sort = sql.getOrderBy().split(",");
            IntStream.range(0, sort.length).forEach(i -> sort[i] = "'" + sort[i]);
            return "{" + String.join(",", sort) + "}";
        }
        return null;
    }

    private BasicDBObject processSelect() {
        BasicDBObject result = new BasicDBObject();
        if (sql.getSelect().equals("*") && sql.getSelect() == null) {
            return new BasicDBObject();
        } else {
            result.put("_id", 0);
            String[] selectedFields = sql.getSelect().split(",");
            for (String selectedField : selectedFields) {
                if (selectedField.equals("id")) {
                    result.remove("_id");
                    continue;
                }
                result.put(selectedField, "1");
            }
            return result;
        }
    }

    private String processWhere() {
        String logicalOperator = "";
        if (sql.getWhere() != null) {
            List<String> logicalOperators = Arrays.asList(AND, OR);
            for (String operator : logicalOperators) {
                if (sqlParser.regExpForWords(operator)) {
                    logicalOperator = operator;
                }
            }
            if (!logicalOperator.equals("")) {
                String[] where = sql.getWhere().split(logicalOperator);
                IntStream.range(0, where.length).forEach(i -> where[i] = "{'" + replacementForWhere(where[i]) + "}}");
                String result = String.join(",", where);
                if (logicalOperator.equals(AND)) {
                    return "{$and:[" + result + "]}";
                } else {
                    return "{$or:[" + result + "]}";
                }
            } else {
                return "{" + replacementForWhere(sql.getWhere()) + "}";
            }
        }
        return "";
    }

    private String replacementForWhere(String replace) {
        return replace != null ? replace.replaceAll("\\b(?:>)\\b", "':{\\$gt:").replaceAll("\\b(?:<)\\b", "':{\\$lt:").replaceAll("\\b(?:<=)\\b", "':{\\$lte:").
                replaceAll("\\b(?:>=)\\b", "':{\\$gte:").replaceAll("<>", "':{\\$ne:").replaceAll("\\b(?:=)", "':{\\$eq:") : null;
    }
}