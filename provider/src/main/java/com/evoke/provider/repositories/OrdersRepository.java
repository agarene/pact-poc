package com.evoke.provider.repositories;

import com.evoke.provider.domain.Order;
import com.evoke.provider.domain.Product;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class OrdersRepository {
    private  Set<Order> orders;
    public OrdersRepository(){
        generateStub();

    }

    private Set<Order> generateStub() {
        orders=new HashSet<>();
        IntStream.range(0,1).forEach((value -> {
            Order order=new Order();
            order.setOrderId(UUID.randomUUID());
            order.setTotal(new BigDecimal("0.00"));
            HashSet<Product> productHashSet =new HashSet<>();

            Product product =new Product();
            product.setCost(new BigDecimal("0.00"));
            product.setProductId(UUID.randomUUID());
            product.setProductName(RandomStringUtils.randomAlphabetic(5));
            product.setQuantity(RandomUtils.nextInt(0,10));

            productHashSet.add(product);
            order.setProducts(productHashSet);
            orders.add(order);
        }));
        return orders;
    }

    public Set<Order> getAll() {
        return orders;
    }

    public Order getOne(UUID orderId) {
        return orders.stream().filter(order ->orderId.equals(orderId)).findFirst().get();
    }
}
