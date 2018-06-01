package com.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Select;
import com.parser.SqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertToMongoQueryImpl implements ConvertToMongoQuery {
    private Select select;

    @Autowired
    private SqlParser sqlParser;

    @Override
    public String processingValues(String query) {
        select = new ObjectMapper().convertValue(sqlParser.invokeParser(query), Select.class);
        return "db." + select.getFrom() + ".find(" + processWhere() + processSelect() + ")" + processOrderBy() + processLimit() + processSkip();
    }

    /**
     * Convert skip sql to skip mongo
     *
     * @return
     */
    private String processSkip() {
        return select.getSkip() != null ? ".skip(" + select.getSkip() + ")" : "";
    }

    /**
     * Convert limit sql to limit mongo
     *
     * @return
     */
    private String processLimit() {
        return select.getLimit() != null ? ".limit(" + select.getLimit() + ")" : "";
    }

    /**
     * Convert skip order by sort mongo
     *
     * @return
     */
    private String processOrderBy() {
        return select.getOrderBy() != null ? ".sort({" + select.getOrderBy() + "})" : "";
    }

    /**
     * Convert select sql to select mongo
     *
     * @return
     */
    private String processSelect() {
        if (select.getSelect().equals("*")) {
            return "{}";
        } else {
            StringBuilder result = new StringBuilder();
            boolean id = false;
            String[] selectedFields = select.getSelect().split(",");
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

    /**
     * Convert where sql to where mongo
     *
     * @return
     */
    private String processWhere() {
        return "";
    }

    /**
     * RegExp for where(sql query)
     *
     * @param replace
     * @return
     */
    private String replacementForWhere(String replace) {
        return replace != null ? replace.replaceAll(">", "\\$gt").replaceAll("<", "\\$lt").replaceAll("<=", "\\$lte").
                replaceAll(">=", "\\$gte").replaceAll("<>", "\\$ne").replaceAll("!=", "\\$ne").
                replaceAll("=", ":") : null;
    }
}