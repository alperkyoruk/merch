package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.CategoryService;
import skylab.skymerch.business.abstracts.ProductService;
import skylab.skymerch.business.abstracts.RatingService;
import skylab.skymerch.business.abstracts.VendorService;
import skylab.skymerch.business.constants.ProductMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.ProductDao;
import skylab.skymerch.entities.Dtos.RequestProductDto;
import skylab.skymerch.entities.Product;

import java.util.List;

@Service
public class ProductManager implements ProductService {


    @Autowired
    private ProductDao productDao;
    private VendorService vendorService;
    private CategoryService categoryService;
    private RatingService ratingService;


    public ProductManager(ProductDao productDao, VendorService vendorService, CategoryService categoryService, RatingService ratingService) {
        this.productDao = productDao;
        this.vendorService = vendorService;
        this.categoryService = categoryService;
        this.ratingService = ratingService;
    }


    @Override
    public Result addProduct(RequestProductDto requestProductDto) {
        if (requestProductDto.getName() == null ||
                requestProductDto.getCategoryId() == 0 ||
                requestProductDto.getVendorId() == 0 ||
                requestProductDto.getPrice() == 0 ||
                requestProductDto.getStock() == 0){
            return new ErrorResult(ProductMessages.ProductCannotBeNull);
    }

        var vendorsResponse = vendorService.getVendorsByProductId(requestProductDto.getVendorId()).getData();
        if(vendorsResponse == null){
            return new ErrorResult(ProductMessages.VendorCannotBeFound);
        }
        var categoryResponse = categoryService.getProductCategory(requestProductDto.getId()).getData();
        if(categoryResponse == null){
            return new ErrorResult(ProductMessages.CategoryCannotBeFound);
        }

        var ratingsResponse = ratingService.getRatingsByProductId(requestProductDto.getId()).getData();
        if(ratingsResponse == null){
            return new ErrorResult(ProductMessages.RatingCannotBeFound);
        }

        Product product =  Product.builder()
                .name(requestProductDto.getName())
                .category(categoryResponse)
                .vendors(vendorsResponse)
                .price(requestProductDto.getPrice())
                .stock(requestProductDto.getStock())
                .ratings(ratingsResponse)
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

        var vendorsResponse = vendorService.getVendorsByProductId(requestProductDto.getVendorId()).getData();
        if(vendorsResponse == null){
            return new ErrorResult(ProductMessages.VendorCannotBeFound);
        }

        var categoryResponse = categoryService.getProductCategory(requestProductDto.getId()).getData();
        if(categoryResponse == null){
            return new ErrorResult(ProductMessages.CategoryCannotBeFound);
        }

        var ratingsResponse = ratingService.getRatingsByProductId(requestProductDto.getId()).getData();
        if(ratingsResponse == null){
            return new ErrorResult(ProductMessages.RatingCannotBeFound);
        }


        var product = Product.builder()
                .id(requestProductDto.getId())
                .name(requestProductDto.getName())
                .category(categoryResponse)
                .vendors(vendorsResponse)
                .price(requestProductDto.getPrice())
                .stock(requestProductDto.getStock())
                .ratings(ratingsResponse)
                .build();

        productDao.save(product);

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
    var result = productDao.findAll();
    if(result.isEmpty()){
        return new ErrorDataResult<>(ProductMessages.getProductsEmpty);
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
