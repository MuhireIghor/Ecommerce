package com.ne.template.serviceImpls;

import com.ne.template.dtos.requests.CreateCategoryDto;
import com.ne.template.exceptions.NotFoundException;
import com.ne.template.models.Category;
import com.ne.template.repositories.ICategoryRepository;
import com.ne.template.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final ICategoryService categoryService;

    @Autowired
    public CategoryServiceImpl(ICategoryRepository categoryRepository, ICategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Category findByCategoryName(String categoryName) {

        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NotFoundException(String.format("Category with name %s is not found", categoryName)));
    }

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public Category createCategory(CreateCategoryDto category) {
        return null;
    }

    @Override
    public Category updateCategory(CreateCategoryDto category) {
        return null;
    }

    @Override
    public void deleteCategory(UUID id) {

    }


}
