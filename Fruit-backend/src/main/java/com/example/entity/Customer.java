package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("customers")
@Data
public class Customer {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String remark;
}
