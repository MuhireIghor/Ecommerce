package com.ne.template.controllers;

import com.ne.template.dtos.requests.CreateProductDto;
import com.ne.template.services.IProductService;
import com.ne.template.utils.ApiResponse;
import com.ne.template.utils.ExceptionUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
