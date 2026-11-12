package com.denison.springecom.repo;

import com.denison.springecom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order,Long> {

    Optional<Order> findByOrderId(String orderId);
}
