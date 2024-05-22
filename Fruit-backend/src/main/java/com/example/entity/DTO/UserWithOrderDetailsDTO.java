package com.example.entity.DTO;

import lombok.Data;
import java.util.List;

@Data
public class UserWithOrderDetailsDTO {

    private Integer userId;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userRemark;
    private List<OrderItemDTO> orders; // 订单列表，每个订单包含订单详情

    // 构造方法
    public UserWithOrderDetailsDTO(Integer userId, String userName, String userPhone, String userAddress, String userRemark,
                                   List<OrderItemDTO> orders) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userRemark = userRemark;
        this.orders = orders;
    }
}


