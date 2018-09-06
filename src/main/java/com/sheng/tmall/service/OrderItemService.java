package com.sheng.tmall.service;

import com.sheng.tmall.pojo.Order;
import com.sheng.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> list();
    void add(OrderItem bean);
    void update(OrderItem bean);
    void delete(int id);
    OrderItem get(int id);

    void fill(List<Order> os);

    void fill(Order o);
    //fore add
    int getSaleCount(int pid);

    List<OrderItem> listByUser(int uid);

    OrderItem get(int uid, int pid);
}
