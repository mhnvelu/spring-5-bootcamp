package com.spring5.projects.springrecipeproject.controllers;

import com.spring5.projects.springrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springrecipeproject.domain.Recipe;
import com.spring5.projects.springrecipeproject.services.IngredientService;
import com.spring5.projects.springrecipeproject.services.RecipeService;
import com.spring5.projects.springrecipeproject.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredients")
    public String listRecipeIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting Ingredients list for Recipe id : " + recipeId);
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService
                .findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{ingredientId}/update")
    public String getUpdateRecipeIngredientForm(@PathVariable String recipeId,
                                                @PathVariable String ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientService
                .findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/new")
    public String getCreateRecipeIngredientForm(@PathVariable String recipeId, Model model) {
        Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipe.getId());
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("/{recipeId}/ingredient")
    public String saveOrUpdateRecipeIngredient(
            @ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand =
                ingredientService.saveIngredient(ingredientCommand);
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" +
               savedIngredientCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId,
                         Model model) {
        ingredientService.deleteByRecipeIdAndIngredientId(Long.valueOf(recipeId),
                                                          Long.valueOf(ingredientId));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
