package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@ToString
@EqualsAndHashCode
public class Select {
    @Getter
    @Setter
    private String select;

    @Getter
    @Setter
    private String from;

    @Getter
    private String where;

    @Getter
    @JsonProperty("order by")
    private String orderBy;

    @Getter
    @Setter
    private String skip;

    @Getter
    @Setter
    private String limit;

    public void setWhere(String where) {
        this.where = where;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy.replaceAll("asc", ":1").replaceAll("desc", ":-1");
    }
}
