package recipes.services;

import org.springframework.stereotype.Service;
import recipes.entities.Recipe;

import java.util.*;


@Service
public interface RecipeService {
    Map<String, ?> saveRecipe(Recipe recipe);

    Recipe findRecipeById(Long id);

    void deleteRecipeById(Long id);

    void updateRecipeById(Recipe recipe, Long id);

    List<Recipe> searchRecipe(String searchBy, String input);

}
