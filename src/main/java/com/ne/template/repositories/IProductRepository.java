package com.ne.template.repositories;

import com.ne.template.models.Category;
import com.ne.template.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByCategory(Category category);

}
