package skylab.skymerch.webAPI.controllers;

import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.ProductService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestProductDto;
import skylab.skymerch.entities.Product;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    public Result addProduct(@RequestBody RequestProductDto requestProductDto){
        return productService.addProduct(requestProductDto);
    }

    @PostMapping("/deleteProduct")
    public Result deleteProduct(@RequestBody int productId){
        return productService.deleteProduct(productId);
    }

    @PostMapping("/updateProduct")
    public Result updateProduct(@RequestBody RequestProductDto requestProductDto){
        return productService.updateProduct(requestProductDto);
    }

    @GetMapping("/getProductById")
    public DataResult<Product> getProductById(@RequestParam int productId){
        return productService.getById(productId);
    }

    @GetMapping("/getProducts")
    public DataResult<List<Product>> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/getProductsBySubcategory")
    public DataResult<List<Product>> getProductsBySubcategory(@RequestParam int subcategoryId){
        return productService.getProductsBySubcategory(subcategoryId);
    }

    @GetMapping("/getProductsByNameConstains")
    public DataResult<List<Product>> getProductsByNameContains(@RequestParam String productName){
        return productService.getProductsByNameContains(productName);
    }

    @GetMapping("/getProductsByPrice")
    public DataResult<List<Product>> getProductsByPrice(@RequestParam float minPrice, @RequestParam float maxPrice){
        return productService.getProductsByPrice(minPrice, maxPrice);
    }

    @GetMapping("/getProductsByStock")
    public DataResult<List<Product>> getProductsByStock(@RequestParam int minStock, @RequestParam int maxStock){
        return productService.getProductsByStock(minStock, maxStock);
    }

    @GetMapping("/getSortedProductsByPrice")
    public DataResult<List<Product>> getSortedProductsByPrice(){
        return productService.getSortedProductsByPrice();
    }

    @GetMapping("/getSortedProductsRating")
    public DataResult<List<Product>> getSortedProductsByRating(){
        return productService.getSortedProductsByRating();
    }

    @GetMapping("/getDiscountedProducts")
    public DataResult<List<Product>> getDiscountedProducts(){
        return productService.getDiscountedProducts();
    }

    @GetMapping("/getProductsByCategory")
    public DataResult<List<Product>> getProductsByCategory(@RequestParam int categoryId){
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/getProductsByVendor")
    public DataResult<List<Product>> getProductsByVendor(@RequestParam int vendorId){
        return productService.getProductsByVendor(vendorId);
    }

















}
