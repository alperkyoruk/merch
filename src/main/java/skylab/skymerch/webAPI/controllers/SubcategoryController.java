package skylab.skymerch.webAPI.controllers;

import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.SubcategoryService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Subcategory;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubcategoryController {

    private SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService){
        this.subcategoryService = subcategoryService;
    }

    @PostMapping("/addSubcategory")
    public Result addSubcategory(@RequestBody Subcategory subcategory){
        return subcategoryService.addSubcategory(subcategory);
    }

    @PostMapping("/deleteSubcategory")
    public Result deleteSubcategory(@RequestBody int subcategoryId){
        return subcategoryService.deleteSubcategory(subcategoryId);
    }

    @PostMapping("/updateSubcategory")
    public Result updateSubcategory(@RequestBody Subcategory subcategory){
        return subcategoryService.updateSubcategory(subcategory);
    }

    @PostMapping("/getSubcategoryById")
    public DataResult<Subcategory> getSubcategoryById(@RequestBody int subcategoryId){
        return subcategoryService.getById(subcategoryId);
    }

    @GetMapping("/getSubcategories")
    public DataResult<List<Subcategory>> getSubcategories(){
        return subcategoryService.getSubcategories();
    }

    @GetMapping("/getSubcategoriesByCategory")
    public DataResult<List<Subcategory>> getSubcategoriesByCategory(@RequestParam int categoryId){
        return subcategoryService.getByCategoryId(categoryId);
    }

    @GetMapping("/getSubcategoriesByName")
    public DataResult<List<Subcategory>> getSubcategoriesByName(@RequestParam String subcategoryName){
        return subcategoryService.getSubcategoriesByNameStartsWith(subcategoryName);
    }





}
