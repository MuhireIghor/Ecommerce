package com.ne.template.controllers;

import com.ne.template.dtos.requests.CreateCategoryDto;
import com.ne.template.dtos.requests.UpdateCategoryDto;
import com.ne.template.services.ICategoryService;
import com.ne.template.utils.ApiResponse;
import com.ne.template.utils.ExceptionUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Categories fetched successfully",
                            categoryService.findAll()
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoriesByCategoryId(@PathVariable(value = "categoryId") UUID categoryId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Category fetched successfully",
                            categoryService.findCategoryById(categoryId)
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody @Valid CreateCategoryDto dto) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Category created successfully",
                            categoryService.createCategory(dto)
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable(value = "categoryId") UUID categoryId, @RequestBody @Valid UpdateCategoryDto dto) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Category updated successfully",
                            categoryService.updateCategory(dto, categoryId)
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(value = "categoryId") UUID categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Category deleted successfully"
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping("/by-category-name/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByCategoryName(@PathVariable(value = "categoryName") String categoryName) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Category retrieved successfully",
                            categoryService.findByCategoryName(categoryName)
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }
}
