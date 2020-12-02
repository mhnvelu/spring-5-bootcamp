package com.spring5.projects.springmongodbrecipeproject.controllers;

import com.spring5.projects.springmongodbrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.ImageReactiveService;
import com.spring5.projects.springmongodbrecipeproject.services.reactive.RecipeReactiveService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private ImageReactiveService imageReactiveService;

    private RecipeReactiveService recipeReactiveService;

    public ImageController(ImageReactiveService imageReactiveService,
                           RecipeReactiveService recipeReactiveService) {
        this.imageReactiveService = imageReactiveService;
        this.recipeReactiveService = recipeReactiveService;
    }

    @GetMapping("/recipe/{recipeId}/imageupload")
    public String getImageUploadForm(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeReactiveService.findCommandById(recipeId).block();
        model.addAttribute("recipe", recipeCommand);
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String saveImageFile(@PathVariable String recipeId,
                                @RequestParam("imagefile") MultipartFile multipartFile) {
        imageReactiveService.saveImageFile(recipeId, multipartFile);
        return "redirect:/recipe/" + recipeId;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public void getRecipeImage(@PathVariable String recipeId,
                               HttpServletResponse httpServletResponse) throws IOException {
        RecipeCommand recipeCommand = recipeReactiveService.findCommandById(recipeId).block();
        if (recipeCommand.getImage() != null) {
            byte[] byteArray = new byte[recipeCommand.getImage().length];
            int i = 0;
            for (Byte wrappedByte : recipeCommand.getImage()) {
                byteArray[i++] = wrappedByte;
            }
            httpServletResponse.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(byteArray);
            IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
        }
    }
}
