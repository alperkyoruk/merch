package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.SubCategory;

import java.util.List;

public interface SubcategoryService {
    Result addSubcategory(SubCategory subCategory);
    Result deleteSubcategory(int subCategoryId);
    Result updateSubcategory(SubCategory subCategory);

    DataResult<SubCategory> getById(int subCategoryId);
    DataResult<List<SubCategory>> getByCategoryId(int categoryId);
    DataResult<List<SubCategory>> getSubcategories();

}
