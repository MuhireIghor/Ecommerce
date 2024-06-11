package com.ne.template.repositories;

import com.ne.template.models.Category;
import com.ne.template.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByCategory(Category category);

    @Query("SELECT p FROM Product p WHERE lower(p.productName) = lower(:productName)")
    Optional<Product> findByProductName(@Param("productName") String productName);
    @Query("SELECT p FROM Product p WHERE lower(p.productName) LIKE lower(concat('%', :searchKey, '%'))")
    List<Product> findByProductNameContaining(@Param("searchKey") String searchKey);

}
