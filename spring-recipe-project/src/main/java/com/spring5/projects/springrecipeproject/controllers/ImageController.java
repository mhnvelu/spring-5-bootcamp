package com.spring5.projects.springrecipeproject.controllers;

import com.spring5.projects.springrecipeproject.commands.RecipeCommand;
import com.spring5.projects.springrecipeproject.services.ImageService;
import com.spring5.projects.springrecipeproject.services.RecipeService;
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

    private ImageService imageService;

    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/imageupload")
    public String getImageUploadForm(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String saveImageFile(@PathVariable String recipeId,
                                @RequestParam("imagefile") MultipartFile multipartFile) {
        imageService.saveImageFile(Long.valueOf(recipeId), multipartFile);
        return "redirect:/recipe/" + recipeId;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public void getRecipeImage(@PathVariable String recipeId,
                               HttpServletResponse httpServletResponse) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
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
