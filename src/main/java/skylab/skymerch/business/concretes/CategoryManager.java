package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.CategoryService;
import skylab.skymerch.business.constants.CategoryMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.CategoryDao;
import skylab.skymerch.entities.Category;

import java.util.List;

@Service
public class CategoryManager implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public CategoryManager(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Result addCategory(Category category) {
       if(category.getName().isEmpty()){
           return new ErrorResult(CategoryMessages.CategoryCannotBeNull);
       }


       categoryDao.save(category);

       return new SuccessResult(CategoryMessages.CategoryAdded);
    }

    @Override
    public DataResult<Category> findById(int id) {
        var  result = categoryDao.findById(id);
        if(result == null){
            return new SuccessDataResult<>(CategoryMessages.categoryCannotBeFound);
        }

        return new SuccessDataResult<>(result, CategoryMessages.getCategoryByIdSuccess);
    }

    @Override
    public DataResult<Category> findByName(String name) {
        var result = categoryDao.findByName(name);
        if(result == null){
            return new SuccessDataResult<>(CategoryMessages.categoryCannotBeFound);
        }

        return new SuccessDataResult<>(result, CategoryMessages.getCategoryByNameSuccess);
    }

    @Override
    public Result deleteCategory(int categoryId) {
        var result = findById(categoryId);
        if(!result.isSuccess()){
            return new ErrorResult(CategoryMessages.categoryCannotBeFound);
        }

        var category = result.getData();
        categoryDao.delete(category);
        return new SuccessResult(CategoryMessages.categoryDeleted);
    }


    @Override
    public DataResult<List<Category>> getCategories() {
        var result = categoryDao.findAll();
        if(result.isEmpty()){
            return new ErrorDataResult<>(CategoryMessages.getCategoriesEmpty);
        }

        return new SuccessDataResult<>(result, CategoryMessages.getCategoriesSuccess);
    }

    @Override
    public DataResult<Category> getProductCategory(int productId) {
        var result = categoryDao.findByProductId(productId);
        if(result == null){
            return new ErrorDataResult<>(CategoryMessages.getCategoriesEmpty);
        }


        return new SuccessDataResult<>(result, CategoryMessages.getCategoriesSuccess);
    }


}
