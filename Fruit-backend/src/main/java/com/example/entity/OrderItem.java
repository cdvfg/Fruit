package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("order_items")
@Data
public class OrderItem {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer orderId;
    private Integer fruitId;
    private Integer quantity;
}
