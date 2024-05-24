package recipes.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import recipes.adapters.UserAdapter;
import recipes.entities.Recipe;
import recipes.entities.Users;
import recipes.errors.RecipeNotFound;
import recipes.errors.UnauthorizedAction;
import recipes.services.UserService;
import recipes.services.RecipeService;

import java.util.*;

@RestController
public class ReceiptController {

    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public ReceiptController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> saveRecipe(@AuthenticationPrincipal UserDetails authentication, @RequestBody @Valid Recipe recipe) {
        var currentUser = userService.findByEmail(authentication.getUsername());
        System.out.println("Current User: " + currentUser);
        Recipe recipeSaved = new Recipe();
        recipeSaved.setName(recipe.getName());
        recipeSaved.setCategory(recipe.getCategory());
        recipeSaved.setDescription(recipe.getDescription());
        recipeSaved.setIngredients(recipe.getIngredients());
        recipeSaved.setDirections(recipe.getDirections());
        recipeSaved.setUsers(currentUser);

        Map<String, ?> response = recipeService.saveRecipe(recipeSaved);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> findRecipeById(
            @PathVariable Long id
    ) {
        Recipe recipe = recipeService.findRecipeById(id);
        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipeById(
            @PathVariable Long id
    ) {
        try {
            recipeService.deleteRecipeById(id);
            return ResponseEntity.noContent().build();
        } catch (RecipeNotFound ex) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedAction ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> updateRecipe(
            @PathVariable Long id,
            @RequestBody @Valid Recipe recipe
    ) {
        try {
            recipeService.updateRecipeById(recipe, id);
            return ResponseEntity.noContent().build();
        } catch (RecipeNotFound ex) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedAction ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/api/recipe/search/")
    public ResponseEntity<?> searchRecipe(
            @RequestParam(value = "name", required = false) Optional<String> searchByName,
            @RequestParam(value = "category", required = false) Optional<String> searchByCategory
    ) {
        if (searchByName.isPresent() && searchByCategory.isEmpty()) {
            return ResponseEntity.ok(recipeService.searchRecipe("name", searchByName.get()));
        } else if (searchByCategory.isPresent() && searchByName.isEmpty()) {
            return ResponseEntity.ok(recipeService.searchRecipe("category", searchByCategory.get()));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
