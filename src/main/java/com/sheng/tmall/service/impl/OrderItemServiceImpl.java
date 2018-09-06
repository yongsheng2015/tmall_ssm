package com.sheng.tmall.service.impl;

import com.sheng.tmall.mapper.OrderItemMapper;
import com.sheng.tmall.pojo.Order;
import com.sheng.tmall.pojo.OrderItem;
import com.sheng.tmall.pojo.OrderItemExample;
import com.sheng.tmall.pojo.Product;
import com.sheng.tmall.service.OrderItemService;
import com.sheng.tmall.service.OrderService;
import com.sheng.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    /**
     *
     */
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @Override
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
    }

    @Override
    public void add(OrderItem bean) {
        orderItemMapper.insert(bean);
    }

    @Override
    public void update(OrderItem bean) {
        orderItemMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem result = orderItemMapper.selectByPrimaryKey(id);
        setProduct(result);
        return result;
    }

    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }
    }

    @Override
    public void fill(Order o) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        setProduct(ois);

        float total=0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
        }

        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(ois);
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid).andOidIsNotNull();
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        int result=0;
        for (OrderItem oi : ois) {
            result+=oi.getNumber();
        }
        return result;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> result = orderItemMapper.selectByExample(example);
        setProduct(result);
        return result;
    }

    @Override
    public OrderItem get(int uid, int pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andPidEqualTo(pid);
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        if(null==ois)
            return null;
        return ois.get(0);
    }

    public void setProduct(List<OrderItem> ois) {
        for (OrderItem oi:ois) {
            setProduct(oi);
        }
    }

    private void setProduct(OrderItem bean) {
        Product product = productService.get(bean.getPid());
        bean.setProduct(product);
    }
}
