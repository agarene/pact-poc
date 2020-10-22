package com.evoke.provider.domain;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public class Order {

         private BigDecimal total;
         private UUID orderId;
         private Set<Product> products;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
