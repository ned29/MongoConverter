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
    private String select;

    private String from;

    private String where;

    @JsonProperty("order by")
    private String orderBy;

    private String skip;

    private String limit;
}