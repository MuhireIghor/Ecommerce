package com.ne.template.serviceImpls;

import com.ne.template.dtos.requests.CreateCategoryDto;
import com.ne.template.dtos.requests.UpdateCategoryDto;
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

    @Autowired
    public CategoryServiceImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @Override
    public Category findByCategoryName(String categoryName) {

        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NotFoundException(String.format("Category with name %s is not found", categoryName)));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CreateCategoryDto dto) {
        Category newCategory = new Category();
        newCategory.setName(dto.getName());
        newCategory.setDescription(dto.getDescription());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category findCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Category with id %s is not found", id)));
    }

    @Override
    public Category updateCategory(UpdateCategoryDto dto, UUID categoryId) {
        boolean isCategoryExists = categoryRepository.existsById(categoryId);

        if (!isCategoryExists) {
            throw new NotFoundException(String.format("Category with id %s is not found", categoryId));
        }
        Category categoryToUpdate = categoryRepository.findById(categoryId).get();
        categoryToUpdate.setDescription(dto.getDescription());
        categoryToUpdate.setName(dto.getName());
        return categoryRepository.save(categoryToUpdate);


    }

    @Override
    public void deleteCategory(UUID id) {
        boolean isCategoryExists = categoryRepository.existsById(id);
        if (!isCategoryExists) {
            throw new NotFoundException(String.format("Category with id %s is not found", id));
        }
        categoryRepository.deleteById(id);


    }


}
