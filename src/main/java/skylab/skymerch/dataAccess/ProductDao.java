package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Product;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    Product findById(int id);
    List<Product> findAllByName(String name);
    List<Product> findByPrice(float price);
    List<Product> findByStock(int stock);
    List<Product> findByDescription(String description);
    List<Product> findByImage(String image);
    List<Product> findAllByCategoryId(int categoryId);
    List<Product> findAllByDiscounted(boolean discounted);
    List<Product> findAllByPriceBetween(float min, float max);
    List<Product> findAllByStockBetween(int min, int max);
    List<Product> findAllByRating(int rating);
    List<Product> findAllByVendors(int vendorId);
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByRatingsDesc();

    List<Product> findAllByNameContainsIgnoreCase(String name);


    List<Product> findAllByCategorySubCategory(int subcategoryId);
}
