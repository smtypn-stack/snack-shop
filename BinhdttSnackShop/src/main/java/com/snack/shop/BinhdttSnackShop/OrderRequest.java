package com.snack.shop.BinhdttSnackShop;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private String customerName;
    private Double totalOriginal;
    private Double totalDiscount;
    private Double totalFinal;
    private Double totalProfit;
    private List<OrderItemRequest> items;

}

