package com.sheng.tmall.service.impl;

import com.sheng.tmall.mapper.ReviewMapper;
import com.sheng.tmall.pojo.Review;
import com.sheng.tmall.pojo.ReviewExample;
import com.sheng.tmall.pojo.User;
import com.sheng.tmall.service.ProductService;
import com.sheng.tmall.service.ReviewService;
import com.sheng.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    /**
     *
     */
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserService userService;

    @Override
    public void add(Review bean) {
        reviewMapper.insert(bean);
    }

    @Override
    public void update(Review bean) {
        reviewMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");

        List<Review> result = reviewMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    public void setUser(List<Review> reviews) {
        for (Review review : reviews) {
            setUser(review);
        }
    }

    private void setUser(Review review) {
        int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }


    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }
}
