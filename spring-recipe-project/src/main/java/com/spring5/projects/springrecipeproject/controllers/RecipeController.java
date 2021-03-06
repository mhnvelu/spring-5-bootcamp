package com.spring5.projects.springrecipeproject.controllers;

import com.spring5.projects.springrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springrecipeproject.domain.Recipe;
import com.spring5.projects.springrecipeproject.exceptions.NotFoundException;
import com.spring5.projects.springrecipeproject.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable(name = "id") String id, Model model) {
        Recipe recipe = recipeService.findById(Long.valueOf(id));
        model.addAttribute("recipe", recipe);
        return "recipe/show";
    }

    @GetMapping("/create")
    public String getCreateRecipeForm(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
            return "recipe/recipeform";
        }
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedRecipeCommand.getId();
    }

    @GetMapping("/{id}/update")
    public String getUpdateRecipeForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/index";
    }

    //Exception Handler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling Not Found Exception...");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

}
