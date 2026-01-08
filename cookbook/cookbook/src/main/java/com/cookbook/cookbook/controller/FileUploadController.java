package com.cookbook.cookbook.controller;

import com.cookbook.cookbook.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * REST Controller for File Upload operations
 */
@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class FileUploadController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private RecipeRepository recipeRepository;

    @PostMapping("/image")
    public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Create uploads directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                : ".jpg";
            String filename = UUID.randomUUID().toString() + extension;

            // Save file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Return the URL path
            String imageUrl = "/uploads/" + filename;

            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);
            response.put("filename", filename);

            return response;
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload image: " + e.getMessage());
            return errorResponse;
        }
    }

    @PostMapping("/recipe/{recipeId}/image")
    public Map<String, String> uploadRecipeImage(
            @PathVariable String recipeId,
            @RequestParam("file") MultipartFile file) {

        Map<String, String> result = uploadImage(file);

        if (!result.containsKey("error")) {
            // Update recipe with image URL
            try {
                Long id = Long.parseLong(recipeId);
                recipeRepository.findById(id).ifPresent(recipe -> {
                    recipe.setImageUrl(result.get("imageUrl"));
                    recipeRepository.save(recipe);
                });
            } catch (NumberFormatException e) {
                result.put("warning", "Invalid recipe ID, image saved but not linked");
            }
        }

        return result;
    }
}
