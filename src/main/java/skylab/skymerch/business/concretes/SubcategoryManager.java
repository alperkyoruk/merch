package skylab.skymerch.business.concretes;

import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.CategoryService;
import skylab.skymerch.business.abstracts.SubcategoryService;
import skylab.skymerch.business.constants.SubcategoryMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.SubcategoryDao;
import skylab.skymerch.entities.Dtos.RequestSubcategoryDto;
import skylab.skymerch.entities.Subcategory;

import java.util.List;

@Service
public class SubcategoryManager implements SubcategoryService {


    private SubcategoryDao subcategoryDao;
    private CategoryService categoryService;

    public SubcategoryManager(SubcategoryDao subcategoryDao, CategoryService categoryService) {
        this.subcategoryDao = subcategoryDao;
        this.categoryService = categoryService;
    }


    @Override
    public Result addSubcategory(RequestSubcategoryDto requestSubcategoryDto) {
        if(requestSubcategoryDto.getName() == null) {
            return new ErrorResult(SubcategoryMessages.SubcategoryNameCannotBeNull);
        }
        var categoryResponse = categoryService.findById(requestSubcategoryDto.getCategoryId()).getData();

        Subcategory subcategory = Subcategory.builder()
                .name(requestSubcategoryDto.getName())
                .category(categoryResponse)
                .build();

        subcategoryDao.save(subcategory);
        return new SuccessResult(SubcategoryMessages.SubcategoryAdded);
    }

    @Override
    public Result deleteSubcategory(int subCategoryId) {
        var result = getById(subCategoryId);
        if(!result.isSuccess()) {
            return new ErrorResult(SubcategoryMessages.SubcategoryCannotBeFound);
        }
        var subCategory = result.getData();
        subcategoryDao.delete(subCategory);

        return new SuccessResult(SubcategoryMessages.SubcategoryDeleted);
    }

    @Override
    public Result updateSubcategory(Subcategory subCategory) {
        if(subCategory.getName() == null) {
            return new ErrorResult(SubcategoryMessages.SubcategoryNameCannotBeNull);
        }
        var subCategoryResponse = subcategoryDao.findById(subCategory.getId());

        if(subCategoryResponse == null) {
            return new ErrorResult(SubcategoryMessages.SubcategoryCannotBeFound);
        }

        subCategoryResponse.setName(subCategory.getName());
        subcategoryDao.save(subCategoryResponse);

        return new SuccessResult(SubcategoryMessages.SubcategoryUpdated);
    }

    @Override
    public DataResult<Subcategory> getById(int subCategoryId) {
        var result = subcategoryDao.findById(subCategoryId);
        if(result == null) {
            return new ErrorDataResult<>(SubcategoryMessages.SubcategoryCannotBeFound);
        }

        return new SuccessDataResult<>(result, SubcategoryMessages.getSubcategoryByIdSuccess);
    }

    @Override
    public DataResult<List<Subcategory>> getByCategoryId(int categoryId) {
        var result = subcategoryDao.findAllByCategoryId(categoryId);
        if(result.isEmpty()) {
            return new ErrorDataResult<>(SubcategoryMessages.SubcategoryCannotBeFound);
        }

        return new SuccessDataResult<>(result, SubcategoryMessages.getSubcategoriesByCategoryIdSuccess);
    }

    @Override
    public DataResult<List<Subcategory>> getSubcategories() {
        var result = subcategoryDao.findAll();
        if(result.isEmpty()) {
            return new ErrorDataResult<>(SubcategoryMessages.SubcategoryCannotBeFound);
        }

        return new SuccessDataResult<>(result, SubcategoryMessages.getSubcategoriesSuccess);
    }


    @Override
    public DataResult<List<Subcategory>> getSubcategoriesByNameStartsWith(String subcategoryName) {
        var result = subcategoryDao.findAllByNameStartsWith(subcategoryName);
        if(result.isEmpty()) {
            return new ErrorDataResult<>(SubcategoryMessages.SubcategoryCannotBeFound);
        }

        return new SuccessDataResult<>(result, SubcategoryMessages.getSubcategoriesByNameStartsWithSuccess);
    }

}
