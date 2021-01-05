package com.spring5.projects.springmongodbrecipeproject.config;

import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.RecipeReactiveService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class WebConfig {

    @Bean
    RouterFunction<?> routes(RecipeReactiveService recipeReactiveService) {
        return RouterFunctions.route(GET("/api/recipes"), serverRequest -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(recipeReactiveService.getRecipes(), Recipe.class));
    }
}
