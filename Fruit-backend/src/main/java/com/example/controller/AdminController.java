package com.example.controller;

import com.example.entity.Customer;
import com.example.entity.DTO.OrderItemDTO;
import com.example.entity.DTO.UserWithOrderDetailsDTO;
import com.example.entity.Fruit;
import com.example.service.CustomerService;
import com.example.service.FruitService;
import com.example.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private FruitService fruitService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private CustomerService customerService;

//1.Mybatis-Plus 不同数据类型查询

//    //     返回 Long 类型的数据(不用redis版)
//    @GetMapping("/long")
//    public Long returnLong() {
//    return fruitService.getById(1).getPrice().longValue();
//}

    //     返回 Long 类型的数据(用redis版)
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;//快了一半
    @GetMapping("/long")
    public Double returnLong() {
        String cacheKey = "price";
        long startTime = System.currentTimeMillis();// 记录开始时间

        // 尝试从缓存中获取价格
        Object cachedPriceObj = redisTemplate.opsForValue().get(cacheKey);
        if (cachedPriceObj != null) {
            long endTime = System.currentTimeMillis();// 记录结束时间
            long duration = endTime - startTime;// 计算时间差

            // 将获取到的对象转换为 Double 类型
            Double cachedPrice = Double.valueOf(cachedPriceObj.toString());
            System.out.println("从缓存中获取价格，耗时 " + duration + " 毫秒");
            return cachedPrice;
        }

        // 如果缓存中没有，则从数据库中获取
        Double price = fruitService.getById(1).getPrice();

        // 将价格存储到缓存中，缓存时间为10分钟
        redisTemplate.opsForValue().set(cacheKey, price, 10, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();// 记录结束时间
        long duration = endTime - startTime;// 计算时间差
        System.out.println("从数据库中获取价格，耗时 " + duration + " 毫秒");

        return price;
    }








    // 返回 String 类型的数据
    @GetMapping("/string")
    public String returnString() {
        return "FBI OPEN DOOR";
    }

    // 返回 Boolean 类型的数据
    @GetMapping("/boolean")
    public Boolean returnBoolean() {
        Fruit fruit = fruitService.getById(1);
        return fruit.getStock() > 0;
    }
    // 返回 Map 类型的数据
    @GetMapping("/map")
    public Map<String, Object> getFruitMap() {
        Fruit fruit = fruitService.getById(1);
        Map<String, Object> result = new HashMap<>();
        result.put("id", fruit.getId());
        result.put("name", fruit.getName());
        result.put("price", fruit.getPrice());
        result.put("stock", fruit.getStock());
        return result;
    }

    // 返回 Object 类型的数据
    @GetMapping("/object")
    public Fruit getFruitObject() {
        return fruitService.getById(1);
    }

    //返回 List<String> 类型的数据
    @GetMapping("/list_string")
    public List<String> getFruitListString() {
        List<Fruit> fruits = fruitService.list();
        List<String> fruitNames = new ArrayList<>();

        for (Fruit fruit : fruits) {
            fruitNames.add(fruit.getName());
        }
        return fruitNames;
    }

    //返回 List<Map> 类型的数据
    @GetMapping("/list_map")
    public List<Map<String, Object>> getFruitListMap() {
        List<Fruit> fruits = fruitService.list();
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Fruit fruit : fruits) {
            Map<String, Object> fruitMap = new HashMap<>();
            fruitMap.put("name", fruit.getName());
            fruitMap.put("price", fruit.getPrice());
            resultList.add(fruitMap);
        }
        return resultList;
    }

    //返回 List<Object> 类型的数据
    @GetMapping("/list_object")
    public List<Fruit> getListObject() {
        List<Fruit> Fruits = fruitService.list();
        return Fruits;
    }

    //返回 Obeject<Object> 类型的数据
    @GetMapping("/object_object")
    public TimeObject getFruitObjectObject() {
        Fruit fruit = fruitService.list().get(0); // 假设这里是获取水果对象的方法
        TimeObject timeObject = new TimeObject();
        timeObject.setTime("10:00:00"); // 假设这里是获取当前时间的方法
        timeObject.setFruit(fruit);

        return timeObject;
    }
    //返回 List<Obeject<Object>> 类型的数据
    @GetMapping("/list_object_object")
    public List<TimeObject> getFruitListObjectObject() {
        List<Fruit> Fruits = fruitService.list();
        List<TimeObject> timeObjectList = new ArrayList<>();
        for (Fruit fruit : Fruits) {
            TimeObject timeObject = new TimeObject();
            timeObject.setTime("10:00:00");
//            if (fruit.getId() == 1){
//                timeObject.setTime("9:00:00");
//            }
//            else if (fruit.getId() == 2){
//                timeObject.setTime("8:00:00");
//            }
//            else {
//                timeObject.setTime("7:00:00");
//            }
            timeObject.setFruit(fruit);
            timeObjectList.add(timeObject);
        }
        return timeObjectList;
    }



    public class TimeObject {
        private String time;
        private Fruit fruit;
        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }

        public Fruit getFruit() {
            return fruit;
        }

        public void setFruit(Fruit fruit) {
            this.fruit = fruit;
        }
    }
//2.Fruit-多表查询-xml
    //给定订单id要求查询订单详情，返回包装好的订单对象，包含着该用户购买的水果和库存
        @GetMapping("/orderId")//携带参数请求
        public OrderItemDTO getOrderDetailsByOrderId(@RequestParam Integer orderId) {
            OrderItemDTO orderItem = orderItemService.getOrderDetailsByOrderId(orderId);
            return orderItem;
        }
        //@GetMapping("/orderId/{orderId}")//携带路径请求参数http://localhost:8181/admin/orderId/1
        //public OrderItemDTO getOrderDetailsByOrderId(@PathVariable Integer orderId) {
        //   return orderItemService.getOrderDetailsByOrderId(orderId);
        //}

    //给用户id，要求返回用户对象里面包含着订单的详情信息，里面包含着该用户购买的水果和库存
    @GetMapping("/userIdANDorderId")
    public UserWithOrderDetailsDTO getUserIdAndOrderDetailsByOrderId(@RequestParam Integer userId) {
        //顾客的信息，订单的信息
        Customer customer = customerService.getById(userId);
        List<OrderItemDTO> orders = orderItemService.getOrdersByUserId(userId);

        return new UserWithOrderDetailsDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getRemark(),
                orders
        );
    }
//3.Fruit-多表查询-快速查询体

    //快速查询-给定订单id要求查询订单详情，返回包装好的订单对象，包含着该用户购买的水果和库存
    @GetMapping("/getOrderDetailsByOrderId_QuickQuery")
    public OrderItemDTO getOrderDetailsByOrderId_QuickQuery(@RequestParam Integer orderId) {
        // 使用 MyBatis Plus 的快速查询功能查询订单详情
        OrderItemDTO orderItem = orderItemService.getOrderDetailsByOrderId_QuickQuery(orderId);
        return orderItem;
    }

    //给用户id，要求返回用户对象里面包含着订单的详情信息，里面包含着该用户购买的水果和库存
    @GetMapping("/getCustomerWithOrderDetailsByCustomerId_QuickQuery")
    public UserWithOrderDetailsDTO getCustomerWithOrderDetailsByCustomerId_QuickQuery(@RequestParam Integer userId) {
        //顾客的信息，订单的信息
        Customer customer = customerService.getById(userId);
        List<OrderItemDTO> orders = orderItemService.getCustomerWithOrderDetailsByCustomerId_QuickQuery(userId);

        return new UserWithOrderDetailsDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getRemark(),
                orders
        );
    }
}
