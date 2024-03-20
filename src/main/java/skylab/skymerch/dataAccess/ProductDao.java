package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {
    Product findById(int id);
    Product findByName(String name);
    Product findByPrice(float price);
    Product findByStock(int stock);
    Product findByDescription(String description);
    Product findByImage(String image);
    Product findAllByCategoryId(int categoryId);
    Product findByDiscounted(boolean discounted);


}
