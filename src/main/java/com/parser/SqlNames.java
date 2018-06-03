package com.parser;

import lombok.Getter;

public enum SqlNames {
    SELECT("SELECT"),
    FROM("FROM"),
    WHERE("WHERE"),
    ORDER_BY("ORDER BY"),
    SKIP("SKIP"),
    LIMIT("LIMIT");

    @Getter
    private final String value;

    SqlNames(String value) {
        this.value = value;
    }
}
