package com.snack.shop.BinhdttSnackShop;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private String id;
    private String customerName;
    private List<OrderItemResponse> items;
    private double totalFinal;
    private double totalDiscount;
    private double totalProfit;
    private String createdAt; // ISO 8601 string

    public OrderResponse(String id, String customerName, List<OrderItemResponse> items, double totalFinal, double totalDiscount, double totalProfit,String createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.items = items;
        this.totalFinal = totalFinal;
        this.totalDiscount = totalDiscount;
        this.createdAt = createdAt;
        this.totalProfit = totalProfit;
    }

    public static class OrderItemResponse {
        private ProductResponse product;
        private int quantity;

        public ProductResponse getProduct() {
            return product;
        }

        public void setProduct(ProductResponse product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        // getters, setters
    }
    public static class ProductResponse {
        private String name;
        private String image;
        private double salePrice;
        private String category;
        private double costPrice;
        // getters, setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
        public double getSalePrice() {
            return salePrice;
        }
        public void setSalePrice(double salePrice) {
            this.salePrice = salePrice;
        }
        public String getCategory() {
            return category;
        }
        public void setCategory(String category) {
            this.category = category;
        }
        public double getCostPrice() {
            return costPrice;
        }
        public void setCostPrice(double costPrice) {
            this.costPrice = costPrice;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(double totalFinal) {
        this.totalFinal = totalFinal;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
