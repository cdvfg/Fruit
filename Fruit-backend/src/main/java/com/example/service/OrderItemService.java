package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.DTO.OrderItemDTO;
import com.example.entity.OrderItem;

import java.util.List;

public interface OrderItemService extends IService<OrderItem> {
    OrderItemDTO getOrderDetailsByOrderId(Integer orderId);

    List<OrderItemDTO> getOrdersByUserId(Integer userId);

    OrderItemDTO getOrderDetailsByOrderId_QuickQuery(Integer orderId);

    List<OrderItemDTO> getCustomerWithOrderDetailsByCustomerId_QuickQuery(Integer userId);
}
