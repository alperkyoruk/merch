package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Category;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer>{
    Category findById(int id);
    Category findByName(String Name);

    Category findByProductId(int productId);


}
