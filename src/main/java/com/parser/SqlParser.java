package com.parser;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface SqlParser {
    Map<String, String> invokeParser(String query);
}
