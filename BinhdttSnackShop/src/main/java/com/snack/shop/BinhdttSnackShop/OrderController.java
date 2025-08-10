package com.snack.shop.BinhdttSnackShop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;
    public OrderController(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            System.out.println("Request nhận từ FE: " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseEntity<?> response = orderService.createOrder(request);
        try {
            // Log response đầu ra
            String resJson = objectMapper.writeValueAsString(response.getBody());
            System.out.println("[OUTPUT] Response trả về FE: " + resJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }
}
