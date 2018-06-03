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

    private static String insertCollectionResult = "";
    private static String result = "";

    @Autowired
    private ConvertToMongoQuery converter;

    @Autowired
    private MongoDBHandler handler;

    @Override
    public String showIndex(Model model) {
        model.addAttribute("query", new Query());
        model.addAttribute("results", result);
        return "index";
    }

    @Override
    public String convertToMongoQuery(@ModelAttribute(value = "query") Query sqlQuery) {
        if (sqlQuery.getSqlQuery() != null) {
            result = converter.processingSql(sqlQuery.getSqlQuery().toLowerCase());
            if (result.equals("[]")) {
                result = "Not Found";
            }
        }
        return "redirect:/";
    }

    @Override
    public String showAddCollections(Model model) {
        model.addAttribute("collections", new Query());
        model.addAttribute("result", insertCollectionResult);
        return "collections";
    }

    @Override
    public String insertCollections(@ModelAttribute(value = "collections") Query collection) {
        if (collection.getCollectionName() != null && collection.getCollection() != null) {
            if (handler.insertDocument(collection.getCollectionName(), collection.getCollection())) {
                insertCollectionResult = "Added";
            }
        } else {
            insertCollectionResult = "Failed";
        }
        return "redirect:/add";
    }
}