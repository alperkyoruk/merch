package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Subcategory;

import java.util.List;

public interface SubcategoryService {
    Result addSubcategory(Subcategory subCategory);
    Result deleteSubcategory(int subCategoryId);
    Result updateSubcategory(Subcategory subCategory);

    DataResult<Subcategory> getById(int subCategoryId);
    DataResult<List<Subcategory>> getByCategoryId(int categoryId);
    DataResult<List<Subcategory>> getSubcategories();

    DataResult<List<Subcategory>> getSubcategoriesByNameStartsWith(String subcategoryName);

}
