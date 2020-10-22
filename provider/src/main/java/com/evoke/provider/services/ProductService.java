package com.evoke.provider.services;

import com.evoke.provider.domain.Order;
import com.evoke.provider.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private OrdersRepository ordersRepository;
    public Set<Order> getAll() {
        return ordersRepository.getAll();
    }

    public Order getOne(UUID orderId) {
        return ordersRepository.getOne(orderId);
    }
}
