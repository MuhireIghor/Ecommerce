package com.ne.template.controllers;

import com.ne.template.dtos.requests.CreateProductDto;
import com.ne.template.dtos.requests.UpdateProductDto;
import com.ne.template.models.Product;
import com.ne.template.services.IProductService;
import com.ne.template.utils.ApiResponse;
import com.ne.template.utils.Constants;
import com.ne.template.utils.ExceptionUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/register-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> registerProduct(@RequestBody @Valid CreateProductDto dto) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Product registed successfully",
                            productService.registerProduct(dto)
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Products successfully fetched",
                            productService.getAllProducts()
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping("/by-product-category/{categoryName}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable(value = "categoryName") String category) {
        try {
            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Product received successfully",
                    productService.getProductsByCategory(category)
            ));

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @PutMapping("/update/{prodId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable(value = "prodId") UUID prodId, @RequestBody @Valid UpdateProductDto dto) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Product updated successfully",
                            productService.updateProduct(dto, prodId)
                    )
            );
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @DeleteMapping("/delete/{prodId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable(value = "prodId") UUID prodId) {
        try {
            productService.deleteProduct(prodId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            true,
                            "Product deleted"
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping("/by-name/{prodName}")
    public ResponseEntity<ApiResponse> getProductsByProductName(@PathVariable(value = "prodName") String prodName) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Product fetched successfully",
                            productService.getProductByName(prodName)
                    )
            );

        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }
    }

    @GetMapping("/all-paginated")
    public ResponseEntity<ApiResponse> getAllPaginatedProducts(
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit
    ) {
        try {
            Pageable pageable = PageRequest.of(page, limit, Sort.Direction.DESC, "id");
            Page<Product> products = productService.getAllPaginatedProducts(pageable);
            if (products.isEmpty()) {
                return ResponseEntity.ok(
                        new ApiResponse(
                                false,
                                "There is no content"
                        )
                );
            }


            return ResponseEntity.ok(new ApiResponse(
                    true,
                    "Products fetched successfully",
                    products
            ));
        } catch (Exception e) {
            return ExceptionUtils.handleControllerExceptions(e);
        }

    }

}
