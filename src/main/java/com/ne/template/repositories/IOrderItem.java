package com.ne.template.repositories;

import com.ne.template.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IOrderItem extends JpaRepository<OrderItem, UUID> {
}
