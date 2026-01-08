package com.cookbook.cookbook.config;

import com.cookbook.cookbook.model.*;
import com.cookbook.cookbook.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSeeder - Seeds initial data and cleans up NULL values
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    // Image URLs - using local files from uploads folder
    private static final String IMG_NASI_GORENG = "/uploads/nasi_goreng.png";
    private static final String IMG_RENDANG = "/uploads/rendang.png";
    private static final String IMG_SATE = "/uploads/sate_ayam.png";
    private static final String IMG_GADO_GADO = "/uploads/gado_gado.png";
    private static final String IMG_SOTO = "/uploads/soto_ayam.png";
    private static final String IMG_DEFAULT = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=500&h=300&fit=crop";

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Seed User 'najwa'
        if (accountRepository.findByUsername("najwa") == null) {
            User najwa = new User("najwa", "password123", "najwa@email.com");
            userRepository.save(najwa);
            System.out.println("Seeded User: najwa");
        }

        // Seed Admin 'admin'
        if (accountRepository.findByUsername("admin") == null) {
            Admin admin = new Admin("admin", "admin123", "admin@email.com");
            adminRepository.save(admin);
            System.out.println("Seeded Admin: admin");
        }

        // Get najwa user for recipe seeding
        Account najwaAccount = accountRepository.findByUsername("najwa");

        // Clean up NULL values in ALL recipes
        cleanupNullValues();

        // Update image URLs
        updateRecipeImages();

        // Seed sample recipes if database is empty
        if (najwaAccount != null && recipeRepository.count() < 5) {
            seedSampleRecipes(najwaAccount);
        }
    }

    /**
     * Fill NULL values with defaults for all recipes
     */
    private void cleanupNullValues() {
        System.out.println("=== CLEANING UP NULL VALUES ===");
        List<Recipe> allRecipes = recipeRepository.findAll();
        int updatedCount = 0;

        for (Recipe recipe : allRecipes) {
            boolean needsUpdate = false;

            // Fix NULL description
            if (recipe.getDescription() == null || recipe.getDescription().isEmpty()) {
                recipe.setDescription("Resep lezat " + recipe.getName() + " yang mudah dibuat di rumah.");
                needsUpdate = true;
            }

            // Fix NULL cook_time
            if (recipe.getCookTime() == null || recipe.getCookTime().isEmpty()) {
                recipe.setCookTime("30 Menit");
                needsUpdate = true;
            }

            // Fix NULL servings
            if (recipe.getServings() == null || recipe.getServings() == 0) {
                recipe.setServings(4);
                needsUpdate = true;
            }

            // Fix NULL image_url
            if (recipe.getImageUrl() == null || recipe.getImageUrl().isEmpty()) {
                recipe.setImageUrl(IMG_DEFAULT);
                needsUpdate = true;
            }

            // Fix empty instructions - add default if empty
            if (recipe.getInstructions() == null || recipe.getInstructions().isEmpty()) {
                List<String> defaultInstructions = new ArrayList<>();
                defaultInstructions.add("1. Siapkan semua bahan.");
                defaultInstructions.add("2. Ikuti langkah-langkah resep.");
                defaultInstructions.add("3. Sajikan selagi hangat.");
                recipe.setInstructions(defaultInstructions);
                needsUpdate = true;
            }

            // Fix empty ingredients - add default if empty
            if (recipe.getIngredients() == null || recipe.getIngredients().isEmpty()) {
                List<String> defaultIngredients = new ArrayList<>();
                defaultIngredients.add("Bahan-bahan sesuai resep");
                recipe.setIngredients(defaultIngredients);
                needsUpdate = true;
            }

            if (needsUpdate) {
                recipeRepository.save(recipe);
                updatedCount++;
                System.out.println("Fixed NULL values for: " + recipe.getName());
            }
        }

        System.out.println("Total recipes updated: " + updatedCount);
        System.out.println("=== END CLEANUP ===");
    }

    /**
     * Update image URLs for known Indonesian recipes
     */
    private void updateRecipeImages() {
        System.out.println("=== UPDATING RECIPE IMAGES ===");
        List<Recipe> allRecipes = recipeRepository.findAll();

        for (Recipe recipe : allRecipes) {
            String name = recipe.getName().toLowerCase();
            String imageUrl = null;

            if (name.contains("nasi goreng")) {
                imageUrl = IMG_NASI_GORENG;
            } else if (name.contains("rendang")) {
                imageUrl = IMG_RENDANG;
            } else if (name.contains("sate") || name.contains("satay")) {
                imageUrl = IMG_SATE;
            } else if (name.contains("gado")) {
                imageUrl = IMG_GADO_GADO;
            } else if (name.contains("soto")) {
                imageUrl = IMG_SOTO;
            }

            if (imageUrl != null && !imageUrl.equals(recipe.getImageUrl())) {
                recipe.setImageUrl(imageUrl);
                recipeRepository.save(recipe);
                System.out.println("Updated image: " + recipe.getName() + " -> " + imageUrl);
            }
        }
        System.out.println("=== END UPDATING IMAGES ===");
    }

    /**
     * Seed sample Indonesian recipes
     */
    private void seedSampleRecipes(Account author) {
        System.out.println("=== SEEDING SAMPLE RECIPES ===");

        createRecipe(author, "Nasi Goreng Spesial",
            List.of("Nasi putih 2 piring", "Telur 2 butir", "Kecap manis 2 sdm", 
                    "Bawang merah 3 siung", "Bawang putih 2 siung", "Cabai rawit 5 buah"),
            List.of("1. Tumis bawang merah dan bawang putih hingga harum.",
                    "2. Masukkan telur, orak-arik.",
                    "3. Tambahkan nasi, aduk rata.",
                    "4. Tuang kecap manis, aduk hingga merata.",
                    "5. Sajikan dengan pelengkap."),
            "Nasi goreng khas Indonesia dengan bumbu spesial yang gurih dan lezat.",
            RecipeStatus.APPROVED, IMG_NASI_GORENG, "30 Menit", 2);

        createRecipe(author, "Rendang Daging",
            List.of("Daging sapi 500g", "Santan kental 400ml", "Lengkuas 3cm", 
                    "Serai 2 batang", "Daun jeruk 5 lembar", "Cabai merah 10 buah"),
            List.of("1. Haluskan bumbu: cabai, bawang merah, bawang putih, lengkuas.",
                    "2. Tumis bumbu halus hingga harum.",
                    "3. Masukkan daging, aduk rata.",
                    "4. Tuang santan, masak dengan api kecil.",
                    "5. Aduk sesekali hingga santan kering dan berminyak."),
            "Rendang daging sapi Padang yang empuk dan kaya rempah.",
            RecipeStatus.APPROVED, IMG_RENDANG, "3 Jam", 6);

        createRecipe(author, "Sate Ayam Madura",
            List.of("Daging ayam 300g", "Kacang tanah goreng 100g", "Kecap manis 3 sdm", 
                    "Bawang merah 5 siung", "Cabai rawit 5 buah", "Jeruk nipis 1 buah"),
            List.of("1. Potong ayam dadu, tusuk dengan tusukan sate.",
                    "2. Bakar sate sambil dioles bumbu kacang.",
                    "3. Buat bumbu kacang: haluskan kacang, cabai, bawang.",
                    "4. Tambahkan kecap dan air jeruk nipis.",
                    "5. Sajikan sate dengan bumbu kacang dan lontong."),
            "Sate ayam khas Madura dengan bumbu kacang yang gurih.",
            RecipeStatus.APPROVED, IMG_SATE, "45 Menit", 4);

        createRecipe(author, "Gado-Gado",
            List.of("Tahu goreng 100g", "Tempe goreng 100g", "Kentang rebus 2 buah", 
                    "Kacang panjang rebus 50g", "Tauge rebus 50g", "Telur rebus 2 butir"),
            List.of("1. Rebus sayuran: kacang panjang, tauge, kentang.",
                    "2. Goreng tahu dan tempe.",
                    "3. Haluskan bumbu kacang.",
                    "4. Tata semua bahan di piring.",
                    "5. Siram dengan bumbu kacang, tambahkan kerupuk."),
            "Gado-gado Jakarta dengan bumbu kacang yang creamy.",
            RecipeStatus.APPROVED, IMG_GADO_GADO, "30 Menit", 2);

        createRecipe(author, "Soto Ayam",
            List.of("Ayam 500g", "Sohun 100g", "Tauge 100g", 
                    "Kunyit 3cm", "Daun seledri", "Telur rebus 2 butir"),
            List.of("1. Rebus ayam dengan bumbu kuning hingga empuk.",
                    "2. Angkat ayam, suwir-suwir dagingnya.",
                    "3. Siapkan sohun yang sudah direndam.",
                    "4. Tata sohun, tauge, suwiran ayam di mangkuk.",
                    "5. Siram dengan kuah panas, taburi bawang goreng."),
            "Soto ayam kuning dengan kuah gurih dan hangat.",
            RecipeStatus.APPROVED, IMG_SOTO, "1 Jam", 4);

        System.out.println("=== SEEDING COMPLETE ===");
    }

    private void createRecipe(Account author, String name, List<String> ingredients,
                              List<String> instructions, String description,
                              RecipeStatus status, String imageUrl, String cookTime, int servings) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setDescription(description);
        recipe.setStatus(status);
        recipe.setUploadedBy(author.getId());
        recipe.setImageUrl(imageUrl);
        recipe.setCookTime(cookTime);
        recipe.setServings(servings);
        recipe.setLikeCount(0);

        recipeRepository.save(recipe);
        System.out.println("Created recipe: " + name);
    }
}
