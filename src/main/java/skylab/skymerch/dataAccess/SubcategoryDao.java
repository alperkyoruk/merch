package skylab.skymerch.dataAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Subcategory;

import java.util.List;

public interface SubcategoryDao extends JpaRepository<Subcategory, Integer> {

    Subcategory findById(int id);
    Subcategory findByName(String subcategoryName);
    List<Subcategory> findAllByNameStartsWith(String subcategoryName);
    Subcategory findByCategoryId(int categoryId);

    List<Subcategory> findAllByCategoryId(int categoryId);


}
