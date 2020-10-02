package com.spring5.projects.springrecipeproject.repositories;

import com.spring5.projects.springrecipeproject.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {
}
