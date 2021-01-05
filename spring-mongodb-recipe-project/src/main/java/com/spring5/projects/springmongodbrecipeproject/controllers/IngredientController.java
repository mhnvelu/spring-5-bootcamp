package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.commands.IngredientCommand;
import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.commands.UnitOfMeasureCommand;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.IngredientReactiveService;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.RecipeReactiveService;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.UnitOfMeasureReactiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {

    private RecipeReactiveService recipeReactiveService;
    private IngredientReactiveService ingredientReactiveService;
    private UnitOfMeasureReactiveService unitOfMeasureReactiveService;

    private WebDataBinder webDataBinder;

    public IngredientController(RecipeReactiveService recipeReactiveService,
                                IngredientReactiveService ingredientReactiveService,
                                UnitOfMeasureReactiveService unitOfMeasureReactiveService) {
        this.recipeReactiveService = recipeReactiveService;
        this.ingredientReactiveService = ingredientReactiveService;
        this.unitOfMeasureReactiveService = unitOfMeasureReactiveService;
    }

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/{recipeId}/ingredients")
    public String listRecipeIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting Ingredients list for Recipe id : " + recipeId);
        Mono<RecipeCommand> recipeCommand = recipeReactiveService.findCommandById(recipeId);
        model.addAttribute("recipe", recipeCommand);
        return "recipe/ingredient/list";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientReactiveService
                .findByRecipeIdAndIngredientId(recipeId, ingredientId));
        return "recipe/ingredient/show";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/update")
    public String getUpdateRecipeIngredientForm(@PathVariable String recipeId,
                                                @PathVariable String ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientReactiveService
                .findByRecipeIdAndIngredientId(recipeId, ingredientId));
//        model.addAttribute("uomList", unitOfMeasureReactiveService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/{recipeId}/ingredient/new")
    public String getCreateRecipeIngredientForm(@PathVariable String recipeId, Model model) {
        Recipe recipe = recipeReactiveService.findById(recipeId).block();
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipe.getId());
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
//        model.addAttribute("uomList", unitOfMeasureReactiveService.listAll());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/{recipeId}/ingredient")
    public String saveOrUpdateRecipeIngredient(
            @ModelAttribute("ingredient") IngredientCommand ingredientCommand, Model model) {

        // workaround for @Valid. it doesnt work for webflux
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
//            model.addAttribute("uomList", unitOfMeasureReactiveService.listAll());
            return "recipe/ingredient/ingredientform";
        }

        IngredientCommand savedIngredientCommand =
                ingredientReactiveService.saveIngredient(ingredientCommand).block();
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" +
               savedIngredientCommand.getId() + "/show";
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId, Model model) {
        ingredientReactiveService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    //Common Model attributes - sets the uomList in the model return from controller to view for
    // all requests.
    @ModelAttribute("uomList")
    public Flux<UnitOfMeasureCommand> populateUomLIst(){
        return unitOfMeasureReactiveService.listAll();
    }
}
