package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.config.WebConfig;
import com.spring5.projects.springmongodbrecipeproject.domain.Recipe;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.RecipeReactiveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

public class RouterFunctionTest {

    WebTestClient webTestClient;

    @Mock
    RecipeReactiveService recipeReactiveService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        WebConfig webConfig = new WebConfig();
        RouterFunction<?> routerFunction = webConfig.routes(recipeReactiveService);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    public void getRecipes() {

        when(recipeReactiveService.getRecipes()).thenReturn(Flux.just());

        webTestClient.get().uri("/api/recipes").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getRecipesWithData() {

        when(recipeReactiveService.getRecipes()).thenReturn(Flux.just(new Recipe()));

        webTestClient.get().uri("/api/recipes").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk().expectBodyList(Recipe.class);
    }
}
