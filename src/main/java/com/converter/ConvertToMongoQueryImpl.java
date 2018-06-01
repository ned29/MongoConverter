package com.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Sql;
import com.parser.SqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertToMongoQueryImpl implements ConvertToMongoQuery {
    private Sql sql;

    @Autowired
    private SqlParser sqlParser;

    @Override
    public String processingSql(String query) {
        sql = new ObjectMapper().convertValue(sqlParser.parseSql(query), Sql.class);
        return "db." + sql.getFrom() + ".find(" + processWhere() + processSelect() + ")" + processOrderBy() + processLimit() + processSkip();
    }

    private String processSkip() {
        return sql.getSkip() != null ? ".skip(" + sql.getSkip() + ")" : "";
    }

    private String processLimit() {
        return sql.getLimit() != null ? ".limit(" + sql.getLimit() + ")" : "";
    }

    private String processOrderBy() {
        String orderBy = sql.getOrderBy().replaceAll("asc", ":1").replaceAll("desc", ":-1");
        return orderBy != null ? ".sort({" + orderBy + "})" : "";
    }

    private String processSelect() {
        if (sql.getSelect().equals("*")) {
            return "{}";
        } else {
            StringBuilder result = new StringBuilder();
            boolean id = false;
            String[] selectedFields = sql.getSelect().split(",");
            for (int i = 0; i < selectedFields.length; i++) {
                if (selectedFields[i].equals("id")) {
                    id = true;
                }
                selectedFields[i] = selectedFields[i] + ":1,";
                result.append(selectedFields[i]);
            }
            result.setLength(result.length() - 1);
            return id ? "{" + result + "}" : "{" + result + ",_id:0}";
        }
    }

    private String processWhere() {
        return "";
    }

    private String replacementForWhere(String replace) {
        return replace != null ? replace.replaceAll(">", "\\$gt").replaceAll("<", "\\$lt").replaceAll("<=", "\\$lte").
                replaceAll(">=", "\\$gte").replaceAll("<>", "\\$ne").replaceAll("!=", "\\$ne").
                replaceAll("=", ":") : null;
    }
}