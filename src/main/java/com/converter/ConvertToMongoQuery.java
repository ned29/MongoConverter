package com.converter;

import org.springframework.stereotype.Component;

@Component
public interface ConvertToMongoQuery {
    String processingValues(String query);
}
