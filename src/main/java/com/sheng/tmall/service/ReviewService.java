package com.sheng.tmall.service;

import com.sheng.tmall.pojo.Review;

import java.util.List;

public interface ReviewService {
    void add(Review bean);
    void update(Review bean);
    void delete(int id);
    Review get(int id);
    List<Review> list(int pid);

    int getCount(int pid);
}
