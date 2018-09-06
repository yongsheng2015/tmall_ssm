package com.sheng.tmall.service.impl;

import com.sheng.tmall.mapper.OrderMapper;
import com.sheng.tmall.pojo.Order;
import com.sheng.tmall.pojo.OrderExample;
import com.sheng.tmall.pojo.OrderItem;
import com.sheng.tmall.pojo.User;
import com.sheng.tmall.service.OrderItemService;
import com.sheng.tmall.service.OrderService;
import com.sheng.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    /**
     *
     */
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemService orderItemService;

    @Override
    public void add(Order bean) {
        orderMapper.insert(bean);
    }

    @Override
    public void update(Order bean) {
        orderMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    public float add(Order o, List<OrderItem> ois) {
        float total = 0;
        add(o);
        for (OrderItem oi : ois) {
            oi.setOid(o.getId());
            orderItemService.update(oi);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }
        return total;
    }

    @Override
    public List<Order> list(int uid, String excludedStatus) {
        OrderExample example =new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result = orderMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    public void setUser(List<Order> beans) {
        for(Order o:beans)
            setUser(o);
    }

    public void setUser(Order bean) {
        User user = userService.get(bean.getUid());
        bean.setUser(user);
    }
}
