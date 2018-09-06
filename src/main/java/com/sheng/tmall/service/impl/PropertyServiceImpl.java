package com.sheng.tmall.service.impl;

import com.sheng.tmall.mapper.PropertyMapper;
import com.sheng.tmall.pojo.Property;
import com.sheng.tmall.pojo.PropertyExample;
import com.sheng.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    /**
     * PropertyServiceImpl 1.0
     */
    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public List<Property> list(int cid) {
        PropertyExample example = new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        return propertyMapper.selectByExample(example);
    }

    @Override
    public void add(Property bean) {
        propertyMapper.insert(bean);
    }

    @Override
    public void update(Property bean) {
        propertyMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public boolean delete(int id) {
        try {
            propertyMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Property get(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }
}
