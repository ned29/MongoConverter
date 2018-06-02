package com.controller;

import com.model.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public interface WebController {

    /**
     * Show page for results
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String showIndex(Model model);

    /**
     * Show page for collections
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    String showAddCollections(Model model);

    /**
     * Post sql for converting in mongo query
     *
     * @param sqlQuery
     * @return
     */
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    String convertToMongoQuery(@ModelAttribute("query") Query sqlQuery);

    /**
     * Post collections to mongo db
     *
     * @param collection
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    String insertCollections(@ModelAttribute("collections") Query collection);
}