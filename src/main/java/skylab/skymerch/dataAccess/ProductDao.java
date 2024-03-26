package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skylab.skymerch.entities.Product;
import skylab.skymerch.entities.Vendor;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    Product findById(int id);
    List<Product> findAllByName(String name);
    List<Product> findAllByCategoryId(int categoryId);
    List<Product> findAllByDiscounted(boolean discounted);
    List<Product> findAllByPriceBetween(float min, float max);
    List<Product> findAllByStockBetween(int min, int max);
    List<Product> findAllByVendors(Vendor vendor);
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByRatingsDesc();

    List<Product> findAllByNameContainsIgnoreCase(String name);


    List<Product> findAllByCategorySubCategoryId(int subcategoryId);

    //Find all by list of ids
    List<Product> findAllByIdIn(List<Integer> ids);

    //List of products by All Ids given.
    @Query("SELECT DISTINCT p FROM Product p WHERE p.id IN :ids")
    List<Product> findAllByIds(List<Integer> ids);
}
