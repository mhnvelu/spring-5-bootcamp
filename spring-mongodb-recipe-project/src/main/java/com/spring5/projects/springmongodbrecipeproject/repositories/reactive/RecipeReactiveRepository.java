package com.spring5.projects.springmongodbrecipeproject.repositories.reactive;

import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
