package com.example.entity.DTO;

import lombok.Data;

@Data
public class OrderItemDTO {

//    private Integer id;
    private Integer orderId;
//    private Integer fruitId;
    private Integer quantity;
    private String fruitName;
    private Double fruitPrice;
    private Integer fruitStock;

    // 构造方法
    public OrderItemDTO(Integer orderId,Integer quantity, String fruitName, Double fruitPrice, Integer fruitStock) {
//        this.id = id;
        this.orderId = orderId;
//        this.fruitId = fruitId;
        this.quantity = quantity;
        this.fruitName = fruitName;
        this.fruitPrice = fruitPrice;
        this.fruitStock = fruitStock;
    }
}
