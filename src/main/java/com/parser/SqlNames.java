package com.parser;

import lombok.Getter;

public enum SqlNames {
    SELECT("select"),
    FROM("from"),
    WHERE("where"),
    ORDER_BY("order by"),
    SKIP("skip"),
    LIMIT("limit");

    @Getter
    private final String value;

    SqlNames(String value) {
        this.value = value;
    }
}
