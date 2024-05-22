package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Customer;
import com.example.entity.DTO.OrderItemDTO;
import com.example.entity.DTO.UserWithOrderDetailsDTO;
import com.example.entity.Fruit;
import com.example.entity.OrderItem;
import com.example.mapper.FruitMapper;
import com.example.mapper.OrderItemMapper;
import com.example.service.FruitService;
import com.example.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private FruitMapper fruitMapper;

    //正常xml写法
    @Override
    public OrderItemDTO getOrderDetailsByOrderId(Integer orderId) {
        Map<String, Object> resultMap = orderItemMapper.getOrderDetailsByOrderId(orderId);
        OrderItemDTO orderItemDTO = new OrderItemDTO(
//                (Integer) resultMap.get("id"),
                (Integer) resultMap.get("order_id"),
//                (Integer) resultMap.get("fruit_id"),
                (Integer) resultMap.get("quantity"),
                (String) resultMap.get("fruit_name"),
                ((BigDecimal) resultMap.get("fruit_price")).doubleValue(),
                (Integer) resultMap.get("fruit_stock")
        );
        return orderItemDTO;
    }
    @Override
    public List<OrderItemDTO> getOrdersByUserId(Integer userId) {
        List<OrderItemDTO> orders = orderItemMapper.getOrdersByUserId(userId);
        return orders;
    }
    //快速查询体,快速查询体感觉就是查单表然后加条件，可以省下个xml这样
    @Override
    public OrderItemDTO getOrderDetailsByOrderId_QuickQuery(Integer orderId) {
        //快速查询-获取订单详情数据，里面的水果id用于查询水果数据
        QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
        orderItemQueryWrapper.eq("order_id", orderId);
        List<OrderItem> orderItems = orderItemMapper.selectList(orderItemQueryWrapper);

        OrderItem orderItem = null;
        if (orderItems.isEmpty()) {
            // 查询结果为空，可以返回空对象或者抛出异常，视情况而定
            throw new IllegalStateException("订单详情列表，查找为空" + orderId);

        } else if (orderItems.size() > 1) {
            // 查询结果不唯一，把查询结果orderItems的多个结果合并
            StringBuilder fruitNamesBuilder = new StringBuilder();
            for (OrderItem order : orderItems) {
                QueryWrapper<Fruit> fruitQueryWrapper = new QueryWrapper<>();
                fruitQueryWrapper.eq("id", order.getFruitId()); // 使用订单项中的水果ID作为查询条件
                Fruit fruit = fruitMapper.selectOne(fruitQueryWrapper);
                String fruitName = fruit.getName();

                if (fruitNamesBuilder.length() > 0) {
                    fruitNamesBuilder.append(", ");
                }
                fruitNamesBuilder.append(fruitName);
            }
            String combinedFruitNames = fruitNamesBuilder.toString();

            // 创建OrderItemDTO对象，并将合并后的水果名称设置进去
            OrderItemDTO orderItemDTO = new OrderItemDTO(null,null,null,null,null);
            orderItemDTO.setFruitName(combinedFruitNames);
            return orderItemDTO;
//            throw new IllegalStateException("订单详情列表，查找为多结果: " + orderId);
        } else {
            // 如果查询结果唯一，直接返回
            orderItem = orderItems.get(0);
            //快速查询-用于查询水果数据
            QueryWrapper<Fruit> fruitQueryWrapper = new QueryWrapper<>();
            fruitQueryWrapper.eq("id", orderItem.getFruitId()); // 使用订单项中的水果ID作为查询条件
            Fruit fruit = fruitMapper.selectOne(fruitQueryWrapper);
            //合并成需要的订单DTO返回
            return new OrderItemDTO(
                    orderItem.getOrderId(),
                    orderItem.getQuantity(),
                    fruit.getName(),
                    fruit.getPrice(),
                    fruit.getStock()
            );
        }
    }

    @Override
    public List<OrderItemDTO> getCustomerWithOrderDetailsByCustomerId_QuickQuery(Integer orderId) {
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        // 查询订单id为orderId的所有订单项数据
        QueryWrapper<OrderItem> orderItemWrapper = new QueryWrapper<>();
        orderItemWrapper.eq("order_id", orderId);
        List<OrderItem> orderItems = orderItemMapper.selectList(orderItemWrapper);

        // 遍历订单项数据
        for (OrderItem orderItem : orderItems) {
            // 获取订单项中的水果id
            Integer fruitId = orderItem.getFruitId();

            // 根据水果id查询水果信息
            QueryWrapper<Fruit> fruitWrapper = new QueryWrapper<>();
            fruitWrapper.eq("id", fruitId);
            Fruit fruit = fruitMapper.selectOne(fruitWrapper);

            // 构建OrderItemDTO对象
            OrderItemDTO orderItemDTO = new OrderItemDTO(
                    orderItem.getId(),
                    orderItem.getQuantity(),
                    fruit.getName(),
                    fruit.getPrice(),
                    fruit.getStock()
            );

            // 将OrderItemDTO对象添加到列表中
            orderItemDTOs.add(orderItemDTO);
        }

        return orderItemDTOs;
    }

}
