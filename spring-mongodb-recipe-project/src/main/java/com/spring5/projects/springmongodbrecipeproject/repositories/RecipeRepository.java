package com.spring5.projects.springmongodbrecipeproject.repositories;

import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
