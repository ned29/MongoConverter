package com.model;

import lombok.Getter;
import lombok.Setter;

/**
 * POJO for thymeleaf
 */
@Getter
@Setter
public class Query {
    private String sqlQuery;
    private String collection;
    private String collectionName;
}