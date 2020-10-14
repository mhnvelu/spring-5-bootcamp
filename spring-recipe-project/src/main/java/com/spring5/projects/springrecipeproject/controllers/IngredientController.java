package com.spring5.projects.springrecipeproject.controllers;

import com.spring5.projects.springrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springrecipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting Ingredients list for Recipe id : " + recipeId);
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);
        return "recipe/ingredient/list";
    }
}
