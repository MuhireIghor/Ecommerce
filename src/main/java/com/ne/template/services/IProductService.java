package com.ne.template.services;

import com.ne.template.dtos.requests.CreateProductDto;
import com.ne.template.dtos.requests.UpdateProductDto;
import com.ne.template.exceptions.ResourceNotFoundException;
import com.ne.template.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    Product getProductById(UUID id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String categoryName) throws ResourceNotFoundException;

    Product registerProduct(CreateProductDto dto);

    Product updateProduct(UpdateProductDto dto,UUID prodId);

    void deleteProduct(UUID id);
    Product getProductByName(String name);
    Page<Product> getAllPaginatedProducts(Pageable pageable);



}
