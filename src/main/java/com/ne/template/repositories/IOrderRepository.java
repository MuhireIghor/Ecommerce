package com.ne.template.repositories;

import com.ne.template.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IOrderRepository extends JpaRepository<Order, UUID> {
}
