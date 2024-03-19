package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Category;

import java.util.List;

public interface CategoryService {

    Result addCategory (Category category);

    DataResult<Category> findById(int id);

    DataResult<Category> findByName(String name);

    Result deleteCategory(int categoryId);

    Result updateCategory(Category category);

    DataResult<List<Category>> getCategories();

    DataResult<List<Category>> getProductCategories(int productId);



}
