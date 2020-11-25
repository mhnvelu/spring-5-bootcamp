package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.services.ImageService;
import com.spring5.projects.springmongodbrecipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void getImageUploadForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/imageupload")).andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        //verify
        verify(recipeService, times(1)).findCommandById(anyString());

    }

    @Test
    void handleImageUpload() throws Exception {
        //given
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                                      ("Spring 5 bootcamp").getBytes());

        //when
        mockMvc.perform(multipart("/recipe/1/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1"));

        //verify
        verify(imageService, times(1)).saveImageFile(anyString(), any());
    }

    @Test
    void renderImageFromDB() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        String img = "Test image";
        Byte bytes[] = new Byte[img.getBytes().length];
        int i = 0;
        for (byte b : img.getBytes()) {
            bytes[i++] = b;
        }

        recipeCommand.setImage(bytes);
        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        //when
        MockHttpServletResponse response =
                mockMvc.perform(get("/recipe/1/image")).andExpect(status().isOk()).andReturn()
                        .getResponse();
        byte[] content = response.getContentAsByteArray();
        assertEquals(img.getBytes().length, content.length);
    }

    @Test
    void getImageNumberFormatException() throws Exception {
        mockMvc.perform(get("/recipe/one/imageupload")).andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}