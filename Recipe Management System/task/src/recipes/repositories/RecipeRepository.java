package recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.entities.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findByNameIgnoreCaseContainsOrderByDateDesc(String name);

}
