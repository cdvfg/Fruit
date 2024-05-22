package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.DTO.OrderItemDTO;
import com.example.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    Map<String, Object> getOrderDetailsByOrderId(Integer orderId);
    List<OrderItemDTO> getOrdersByUserId(Integer userId);
}
