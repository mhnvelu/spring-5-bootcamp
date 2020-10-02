package com.spring5.projects.springrecipeproject.services;

import com.spring5.projects.springrecipeproject.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
