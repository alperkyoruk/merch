package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestProductDto;
import skylab.skymerch.entities.Product;

import java.util.List;

public interface ProductService {

    Result addProduct(RequestProductDto requestProductDto);

    Result deleteProduct(int productId);

    Result updateProduct(RequestProductDto requestProductDto);

    DataResult<Product> getById(int productId);

    DataResult<List<Product>> getProducts();

    DataResult<List<Product>> getProductsByName(String productName);

    DataResult<List<Product>> getProductsByCategory(int categoryId);

    DataResult<List<Product>> getProductsByPrice(float minPrice, float maxPrice);

    DataResult<List<Product>> getProductsByStock(int minStock, int maxStock);

    DataResult<List<Product>> getDiscountedProducts();

    DataResult<List<Product>> getProductsByVendor(int vendorId);

    DataResult<List<Product>> getSortedProductsByPrice();

    DataResult<List<Product>> getSortedProductsByRating();

    DataResult<List<Product>> getProductsBySubcategory(int subcategoryId);

    DataResult<List<Product>> getProductsByNameContains(String productName);








}
