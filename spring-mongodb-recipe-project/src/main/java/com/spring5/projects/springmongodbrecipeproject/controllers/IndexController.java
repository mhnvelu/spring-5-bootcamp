package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.services.reactive.RecipeReactiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeReactiveService recipeReactiveService;

    public IndexController(RecipeReactiveService recipeReactiveService) {
        this.recipeReactiveService = recipeReactiveService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.info("Getting index page");
        model.addAttribute("recipes", recipeReactiveService.getRecipes().collectList().block());
        return "index";
    }
}
