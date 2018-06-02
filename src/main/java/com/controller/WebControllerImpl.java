package com.controller;

import com.converter.ConvertToMongoQuery;
import com.model.Query;
import com.mongoHandler.MongoDBHandler;
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
        converter.processingSql(sqlQuery.getSqlQuery().toLowerCase());
        return "index";
    }

    @Override
    public String showAddCollections(Model model) {
        model.addAttribute("collections", new Query());
        return "collections";
    }

    @Override
    public String insertCollections(@ModelAttribute(value = "collections") Query collection) {
        handler.insertDocument(collection.getCollectionName(),collection.getCollection());
        return "collections";
    }
}