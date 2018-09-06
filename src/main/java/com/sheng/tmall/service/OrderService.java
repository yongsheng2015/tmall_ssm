package com.sheng.tmall.service;

import com.sheng.tmall.pojo.Order;
import com.sheng.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    float add(Order o, List<OrderItem> ois);//fore add
    List<Order> list(int uid, String excludedStatus);//fore add

    List<Order> list();
    void add(Order bean);
    void update(Order bean);
    void delete(int id);
    Order get(int id);
}
