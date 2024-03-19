package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Product;

import java.util.List;

public interface ProductService {

    Result addProduct(Product product);

    Result deleteProduct(int productId);

    Result updateProduct(Product product);

    Result changeProductStock(int productId, int stock);

    Result changeProductPrice(int productId, float price);

    DataResult<Product> getById(int productId);

    DataResult<List<Product>> getProducts();

    DataResult<List<Product>> getProductsByName(String productName);

    DataResult<List<Product>> getProductsByCategory(int categoryId);

    DataResult<List<Product>> getProductsByPrice(float minPrice, float maxPrice);

    DataResult<List<Product>> getProductsByStock(int minStock, int maxStock);

    DataResult<List<Product>> getDiscountedProducts();

    DataResult<List<Product>> getProductsByVendor(int vendorId);

    DataResult<List<Product>> getSortedProductsByPrice();

    DataResult<List<Product>> getSortedProductsByRating(int rating);







}
