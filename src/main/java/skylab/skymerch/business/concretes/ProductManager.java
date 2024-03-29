package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.*;
import skylab.skymerch.business.constants.ProductMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.ProductDao;
import skylab.skymerch.entities.Dtos.RequestProductDto;
import skylab.skymerch.entities.Product;

import java.util.Date;
import java.util.List;

@Service
public class ProductManager implements ProductService {


    @Autowired
    private ProductDao productDao;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubcategoryService subcategoryService;


    @Override
    public Result addProduct(RequestProductDto requestProductDto) {
        if (requestProductDto.getName() == null ||
                requestProductDto.getCategoryId() == 0 ||
                requestProductDto.getPrice() == 0 ||
                requestProductDto.getStock() == 0){
            return new ErrorResult(ProductMessages.ProductCannotBeNull);
    }

        var vendorsResponse = vendorService.getVendorsByIds(requestProductDto.getVendors()).getData();

        var categoryResponse = categoryService.findById(requestProductDto.getCategoryId()).getData();
        if(categoryResponse == null){
            return new ErrorResult(ProductMessages.CategoryCannotBeFound);
        }

        var subcategoryResponse = subcategoryService.getById(requestProductDto.getSubcategoryId()).getData();

        Product product =  Product.builder()
                .name(requestProductDto.getName())
                .description(requestProductDto.getDescription())
                .category(categoryResponse)
                .createdAt(new Date())
                .image(requestProductDto.getImage())
                .discounted(requestProductDto.isDiscounted())
                .vendors(vendorsResponse)
                .subcategory(subcategoryResponse)
                .averageRating(0)
                .price(requestProductDto.getPrice())
                .stock(requestProductDto.getStock())
                .build();

        productDao.save(product);

        return new SuccessResult(ProductMessages.ProductAdded);
    }

    @Override
    public Result deleteProduct(int productId) {
        var result = getById(productId);
        if(!result.isSuccess()) {
            return new ErrorResult(ProductMessages.ProductCannotBeFound);
        }

        var product = result.getData();
        productDao.delete(product);
        return new SuccessResult(ProductMessages.ProductDeleted);
    }

    @Override
    public Result updateProduct(RequestProductDto requestProductDto) {
        var result = productDao.findById(requestProductDto.getId());
        if(result == null){
            return new ErrorResult(ProductMessages.ProductCannotBeFound);
        }

        var vendorsResponse = vendorService.getVendorsByIds(requestProductDto.getVendors()).getData();


        var categoryResponse = categoryService.getProductCategory(requestProductDto.getId()).getData();




                result.setName(requestProductDto.getName() == null ? result.getName() : requestProductDto.getName());
                result.setDescription(requestProductDto.getDescription().isEmpty() ? result.getDescription() : requestProductDto.getDescription());
                result.setImage(requestProductDto.getImage() == null ? result.getImage() : requestProductDto.getImage());
                result.setUpdatedAt(new Date());
                result.setDiscounted(requestProductDto.isDiscounted());
                result.setCategory(categoryResponse == null ? result.getCategory() : categoryResponse);
                result.setVendors(vendorsResponse == null ? result.getVendors() : vendorsResponse);
                result.setPrice(requestProductDto.getPrice() == 0 ? result.getPrice() : requestProductDto.getPrice());
                result.setStock(requestProductDto.getStock() == 0 ? result.getStock() : requestProductDto.getStock());
                result.setDiscountRate(requestProductDto.getDiscountRate() == 0 ? result.getDiscountRate() : requestProductDto.getDiscountRate());
                result.setCreatedAt(result.getCreatedAt());
                result.setSubcategory(result.getSubcategory());
                result.setAverageRating(result.getAverageRating());

        productDao.save(result);

        return new SuccessResult(ProductMessages.ProductUpdated);
    }


    @Override
    public DataResult<Product> getById(int productId) {
        var result = productDao.findById(productId);
        if(result == null){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductByIdSuccess);
    }

    @Override
    public DataResult<List<Product>> getProducts() {
    var result = productDao.findAllByOrderByIdAsc();
    if(result.isEmpty()){
        return new ErrorDataResult<>(ProductMessages.getProductsEmpty);
    }
        return new SuccessDataResult<>(result, ProductMessages.getProductsSuccess);
    }

    public DataResult<List<Product>> getProductsByIds(List<Integer> productIds) {
        var result = productDao.findAllByIdIn(productIds);

        if(result.isEmpty()){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsSuccess);
    }


    @Override
    public DataResult<List<Product>> getProductsByName(String productName) {
        var result = productDao.findAllByName(productName);
        if(result == null){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsByNameSuccess);
    }

    @Override
    public DataResult<List<Product>> getProductsByCategory(int categoryId) {
        var result = productDao.findAllByCategoryId(categoryId);
        if(result == null){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsByCategorySuccess);
    }

    @Override
    public DataResult<List<Product>> getProductsByPrice(float minPrice, float maxPrice) {
        var result = productDao.findAllByPriceBetween(minPrice, maxPrice);
        if(result == null){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsByPriceSuccess);
    }

    @Override
    public DataResult<List<Product>> getProductsByStock(int minStock, int maxStock) {
        var result = productDao.findAllByStockBetween(minStock, maxStock);
        if(result == null){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsByStockSuccess);
    }

    @Override
    public DataResult<List<Product>> getDiscountedProducts() {
        var result = productDao.findAllByDiscounted(true);
        if(result == null){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsSuccess);
    }

    @Override
    public DataResult<List<Product>> getProductsByVendor(int vendorId) {
        var vendorResponse = vendorService.getVendorById(vendorId).getData();
        if(vendorResponse == null){
            return new ErrorDataResult<>(ProductMessages.VendorCannotBeFound);
        }
        var result = productDao.findAllByVendors(vendorResponse);
        if(result == null){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsSuccess);
    }

    @Override
    public DataResult<List<Product>> getSortedProductsByPrice() {
        var result = productDao.findAllByOrderByPriceAsc();
        if(result == null){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsByPriceSuccess);
    }

    @Override
    public DataResult<List<Product>> getSortedProductsByRating() {
        var result = productDao.findAllByOrderByRatingsDesc();
        if(result == null){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsByRatingSuccess);
    }

    @Override
    public DataResult<List<Product>> getProductsBySubcategory(int subcategoryId) {
        var result = productDao.findAllByCategorySubCategoryId(subcategoryId);
        if(result.isEmpty()){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);

        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsBySubcategorySuccess);
    }

    @Override
    public DataResult<List<Product>> getProductsByNameContains(String productName) {
        var result = productDao.findAllByNameContainsIgnoreCase(productName);
        if(result.isEmpty()){
            return new ErrorDataResult<>(ProductMessages.ProductCannotBeFound);
        }

        return new SuccessDataResult<>(result, ProductMessages.getProductsByNameSuccess);
    }


}
