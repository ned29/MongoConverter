package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ToString
@EqualsAndHashCode
public class Sql {

    @JsonProperty("SELECT")
    private String select;

    @JsonProperty("FROM")
    private String from;

    @JsonProperty("WHERE")
    private String where;

    @JsonProperty("ORDER BY")
    private String orderBy;

    @JsonProperty("SKIP")
    private String skip;

    @JsonProperty("LIMIT")
    private String limit;
}