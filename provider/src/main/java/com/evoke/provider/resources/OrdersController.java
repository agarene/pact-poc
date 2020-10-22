package com.evoke.provider.resources;

import com.evoke.provider.domain.Order;
import com.evoke.provider.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private ProductService productService;
    @GetMapping("")
    public ResponseEntity<Set<Order>> getProducts(){
        return ResponseEntity.ok(productService.getAll());
    }
    @GetMapping("/getOne/{orderId}")
    public ResponseEntity<Order> getProducts(@PathVariable(value = "orderId")UUID orderId){
        return ResponseEntity.ok(productService.getOne(orderId));
    }
}
