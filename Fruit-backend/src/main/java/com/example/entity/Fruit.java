package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("fruits")
@Data
public class Fruit {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Double price;

    private Integer stock;
}
