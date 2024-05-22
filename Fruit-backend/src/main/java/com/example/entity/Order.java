package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
@TableName("orders")
@Data
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer customerId;
    private Date orderDate;
    private Boolean paid;
}
