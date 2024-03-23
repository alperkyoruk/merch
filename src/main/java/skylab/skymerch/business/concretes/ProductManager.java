package skylab.skymerch.business.concretes;

import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.ProductService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Product;

import java.util.List;

@Service
public class ProductManager implements ProductService {
    @Override
    public Result addProduct(Product product) {
        return null;
    }

    @Override
    public Result deleteProduct(int productId) {
        return null;
    }

    @Override
    public Result updateProduct(Product product) {
        return null;
    }

    @Override
    public Result changeProductStock(int productId, int stock) {
        return null;
    }

    @Override
    public Result changeProductPrice(int productId, float price) {
        return null;
    }

    @Override
    public DataResult<Product> getById(int productId) {
        return null;
    }

    @Override
    public DataResult<List<Product>> getProducts() {
        return null;
    }

    @Override
    public DataResult<List<Product>> getProductsByName(String productName) {
        return null;
    }

    @Override
    public DataResult<List<Product>> getProductsByCategory(int categoryId) {
        return null;
    }

    @Override
    public DataResult<List<Product>> getProductsByPrice(float minPrice, float maxPrice) {
        return null;
    }

    @Override
    public DataResult<List<Product>> getProductsByStock(int minStock, int maxStock) {
        return null;
    }

    @Override
    public DataResult<List<Product>> getDiscountedProducts() {
        return null;
    }

    @Override
    public DataResult<List<Product>> getProductsByVendor(int vendorId) {
        return null;
    }

    @Override
    public DataResult<List<Product>> getSortedProductsByPrice() {
        return null;
    }

    @Override
    public DataResult<List<Product>> getSortedProductsByRating(int rating) {
        return null;
    }
}
