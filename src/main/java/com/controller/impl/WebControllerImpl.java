package com.controller.impl;

import com.controller.WebController;
import com.converter.ConvertToMongoQuery;
import com.model.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
public class WebControllerImpl implements WebController {

    @Autowired
    private ConvertToMongoQuery converter;

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
}
