package com.controller;

import com.converter.ConvertToMongoQuery;
import com.model.Query;
import com.mongodb.MongoDBHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
public class WebControllerImpl implements WebController {

    @Autowired
    private ConvertToMongoQuery converter;

    @Autowired
    private MongoDBHandler handler;

    @Override
    public String showIndex(Model model) {
        model.addAttribute("query", new Query());
        return "index";
    }

    @Override
    public String convertToMongoQuery(@ModelAttribute(value = "query") Query sqlQuery) {
        converter.processingValues(sqlQuery.getSqlQuery().toLowerCase());
        return "index";
    }

    @Override
    public String showAddCollections(Model model) {
        model.addAttribute("collections", new Query());
        return "add-collections";
    }

    @Override
    public String insertCollections(@ModelAttribute(value = "collections") Query sqlQuery) {
        return "add-collections";
    }
}