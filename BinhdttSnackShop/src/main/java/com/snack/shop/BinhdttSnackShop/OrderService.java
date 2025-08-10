package com.snack.shop.BinhdttSnackShop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ResponseEntity<?> createOrder(OrderRequest request) {
        try {
            // Gom số lượng theo productId
            Map<String, Integer> productQuantityMap = new java.util.HashMap<>();
            for (var itemReq : request.getItems()) {
                String productId = itemReq.getProduct().getId();
                int quantity = itemReq.getQuantity();
                productQuantityMap.put(productId, productQuantityMap.getOrDefault(productId, 0) + quantity);
            }
            // Trừ stock một lần cho mỗi product
            Map<String, Product> productMap = new java.util.HashMap<>();
            for (var entry : productQuantityMap.entrySet()) {
                String productId = entry.getKey();
                int totalQuantity = entry.getValue();

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

                int newStock = product.getStock() - totalQuantity;
                if (newStock < 0) {
                    throw new OutOfStockException("Không đủ hàng trong kho cho sản phẩm: " + product.getName());
                }

                product.setStock(newStock);
                productRepository.save(product);
                productMap.put(productId, product); // lưu để dùng sau
            }

            // Tạo Order
            Order order = new Order();
            order.setCustomerName(request.getCustomerName());
            order.setTotalOriginal(request.getTotalOriginal() != null ? request.getTotalOriginal() : 0);
            order.setTotalDiscount(request.getTotalDiscount() != null ? request.getTotalDiscount() : 0);
            order.setTotalFinal(request.getTotalFinal() != null ? request.getTotalFinal() : 0);
            order.setTotalProfit(request.getTotalProfit() != null ? request.getTotalProfit() : 0);
            List<OrderItem> items = new ArrayList<>();
            for (var itemReq : request.getItems()) {
                String productId = itemReq.getProduct().getId();
                Product product = productMap.get(productId);

                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(itemReq.getQuantity());
                item.setSalePrice(product.getSalePrice());
                item.setCostPrice(product.getCostPrice());

                items.add(item);
            }

            order.setItems(items);
            Order savedOrder = orderRepository.save(order);

            // Trả về 200 kèm dữ liệu
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("Orderid",savedOrder.getId()));

        } catch (OutOfStockException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new GlobalExceptionHandler.ErrorResponse(409,e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi tạo đơn hàng: " + e.getMessage());
        }
    }
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> {
            List<OrderResponse.OrderItemResponse> items = order.getItems().stream()
                    .map(item -> {
                        OrderResponse.ProductResponse productResponse = new OrderResponse.ProductResponse();
                        productResponse.setName(item.getProduct().getName());
                        productResponse.setImage(item.getProduct().getImage()); // Hoặc getImage()
                        productResponse.setSalePrice(item.getProduct().getSalePrice());
                        productResponse.setCategory(item.getProduct().getCategory());
                        productResponse.setCostPrice(item.getProduct().getCostPrice());
                        OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
                        itemResponse.setProduct(productResponse);
                        itemResponse.setQuantity(item.getQuantity());

                        return itemResponse;
                    }).collect(Collectors.toList());

            // Format createdAt to ISO 8601 string nếu cần
            String createdAtIso = order.getCreatedAt()
                    .atZone(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

            return new OrderResponse(
                    order.getId(),
                    order.getCustomerName(),
                    items,
                    order.getTotalFinal(),
                    order.getTotalDiscount(),
                    order.getTotalProfit(),
                    createdAtIso
            );
        }).collect(Collectors.toList());
    }
}
