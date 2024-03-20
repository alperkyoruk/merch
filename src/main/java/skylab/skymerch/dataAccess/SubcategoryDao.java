package skylab.skymerch.dataAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Subcategory;

public interface SubcategoryDao extends JpaRepository<Subcategory, Integer> {

    Subcategory findById(int id);
    Subcategory findByName(String subcategoryName);
    Subcategory findByCategoryId(int categoryId);

    Subcategory findAllByCategoryId(int categoryId);


}
