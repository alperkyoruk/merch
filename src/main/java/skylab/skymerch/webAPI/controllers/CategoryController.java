package skylab.skymerch.webAPI.controllers;

import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.CategoryService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Category;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @PostMapping("/deleteCategory")
    public Result deleteCategory(@RequestBody int categoryId){
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/getCategoryById")
    public DataResult<Category> getCategoryById(@RequestParam int categoryId){
        return categoryService.findById(categoryId);
    }

    @GetMapping("/getCategories")
    public DataResult<List<Category>> getCategories(){
        return categoryService.getCategories();
    }

    @GetMapping("/getCategoryByName")
    public DataResult<Category> getCategoryByName(@RequestParam String categoryName){
        return categoryService.findByName(categoryName);
    }

    @GetMapping("/getProductCategory")
    public DataResult<Category> getProductCategory(@RequestParam int productId){
        return categoryService.getProductCategory(productId);
    }




}
