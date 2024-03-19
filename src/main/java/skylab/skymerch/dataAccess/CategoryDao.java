package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Category;

public interface CategoryDao extends JpaRepository<Category, Integer>{
    Category findById(int id);
    Category findByCategoryName(String categoryName);


}
