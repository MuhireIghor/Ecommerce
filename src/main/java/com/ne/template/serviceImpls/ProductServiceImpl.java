package com.ne.template.serviceImpls;

import com.ne.template.dtos.requests.CreateProductDto;
import com.ne.template.dtos.requests.UpdateProductDto;
import com.ne.template.exceptions.BadRequestAlertException;
import com.ne.template.exceptions.NotFoundException;
import com.ne.template.exceptions.ResourceNotFoundException;
import com.ne.template.models.Category;
import com.ne.template.models.Product;
import com.ne.template.repositories.IProductRepository;
import com.ne.template.services.ICategoryService;
import com.ne.template.services.IProductService;
import com.ne.template.utils.ExceptionUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryService categoryService;

    @Autowired
    public ProductServiceImpl(IProductRepository productRepository, ICategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Product with id %s is not found", id)));

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) throws ResourceNotFoundException {
        try {
            Category category = categoryService.findByCategoryName(categoryName);
            return productRepository.findAllByCategory(category);

        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public Product registerProduct(CreateProductDto dto) {
        Category productCategory = categoryService.findByCategoryName(dto.getCategoryName());
        if (productCategory == null) {
            throw new BadRequestAlertException(String.format("Category %s is not found", dto.getCategoryName()));
        }
        Product product = new Product();
        product.setCategory(productCategory);
        product.setProductName(dto.getProductName());
        product.setProductDescription(dto.getProductDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Product updateProduct(UpdateProductDto dto, UUID prodId) {
        Product productToUpdate = productRepository.findById(prodId).orElseThrow(() -> new NotFoundException("Product with id " + prodId + " is not found"));
        productToUpdate.setProductName(dto.getProductName());
        productToUpdate.setProductDescription(dto.getProductDescription());
        productToUpdate.setPrice(dto.getPrice());
        productToUpdate.setStockQuantity(dto.getStockQuantity());
        return productRepository.save(productToUpdate);

    }

    @Override
    public void deleteProduct(UUID id) {
        boolean isProdExistent = productRepository.findById(id).isPresent();
        if (!isProdExistent) {
            throw new NotFoundException(String.format("Product with id %s is not found", id));
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByProductName(name).orElseThrow(() -> new NotFoundException(String.format("Product with name %s is not found", name)));

    }

    @Override
    public Page<Product> getAllPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
