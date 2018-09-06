package com.sheng.tmall.service;

import com.sheng.tmall.pojo.Property;

import java.util.List;

public interface PropertyService {
    List<Property> list(int cid);
    void add(Property bean);
    void update(Property bean);
    boolean delete(int id);
    Property get(int id);
}
