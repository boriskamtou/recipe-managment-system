package recipes.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import recipes.adapters.UserAdapter;
import recipes.entities.Recipe;
import recipes.entities.Users;
import recipes.errors.RecipeNotFound;
import recipes.errors.UnauthorizedAction;
import recipes.repositories.RecipeRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository repository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Map<String, ?> saveRecipe(Recipe recipe) {
        Recipe savedRecipe = repository.save(recipe);
        return Map.of("id", savedRecipe.getId());
    }

    @Override
    public Recipe findRecipeById(Long id) {
        List<Recipe> recipeList = repository.findAll();
        for (var recipe : recipeList) {
            if (recipe.getId() == id) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public void deleteRecipeById(Long id) {
        Optional<Recipe> recipeToDelete = repository.findById(id);
        if (recipeToDelete.isPresent()) {
            UserAdapter currentUer = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (currentUer.getUsername().equals(recipeToDelete.get().getUsers().getEmail())) {
                repository.deleteById(id);
            } else {
                throw new UnauthorizedAction();
            }
        } else {
            throw new RecipeNotFound();
        }
    }

    @Override
    public void updateRecipeById(Recipe recipe, Long id) {
        Optional<Recipe> optionalRecipe = repository.findById(id);
        if (optionalRecipe.isPresent()) {
            UserAdapter currentUer = (UserAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            System.out.println("Current user when updated: " + currentUer.getUsername());

            var findedRecipe = optionalRecipe.get();
            if (currentUer.getUsername().equals(findedRecipe.getUsers().getEmail())) {
                findedRecipe.setName(recipe.getName());
                findedRecipe.setDescription(recipe.getDescription());
                findedRecipe.setIngredients(recipe.getIngredients());
                findedRecipe.setDirections(recipe.getDirections());
                findedRecipe.setCategory(recipe.getCategory());
                findedRecipe.setDate(LocalDateTime.now());
                repository.save(findedRecipe);
            } else {
                throw new UnauthorizedAction();
            }
        } else {
            throw new RecipeNotFound();
        }
    }


    @Override
    public List<Recipe> searchRecipe(String searchBy, String input) {
        List<Recipe> recipeList = new ArrayList<>();
        if (searchBy.equals("name")) {
            return repository.findByNameIgnoreCaseContainsOrderByDateDesc(input);
        } else if (searchBy.equals("category")) {
            return repository.findByCategoryIgnoreCaseOrderByDateDesc(input);
        } else {
            return recipeList;
        }
    }
}
