package com.ne.template.services;

import com.ne.template.dtos.requests.CreateCategoryDto;
import com.ne.template.dtos.requests.UpdateCategoryDto;
import com.ne.template.models.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoryService {

    List<Category> findAll();
    Category createCategory(CreateCategoryDto category);
    Category findCategoryById(UUID id);
    Category updateCategory(UpdateCategoryDto category,UUID categoryId);
    void deleteCategory(UUID id);
    Category findByCategoryName(String categoryName);


}
