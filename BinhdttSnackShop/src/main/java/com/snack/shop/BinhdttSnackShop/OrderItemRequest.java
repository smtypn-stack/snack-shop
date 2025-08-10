package com.snack.shop.BinhdttSnackShop;

public class OrderItemRequest {
    private String id;
    private ProductRequest product;
    private int quantity;

    public OrderItemRequest(String id, ProductRequest product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductRequest getProduct() {
        return product;
    }

    public void setProduct(ProductRequest product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
