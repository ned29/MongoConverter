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
     *Show page
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String showIndex(Model model);

    /**
     * Post sql for converting in mongo query
     * @param sqlQuery
     * @return
     */
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    String convertToMongoQuery(@ModelAttribute("sqlQuery") Query sqlQuery);
}
