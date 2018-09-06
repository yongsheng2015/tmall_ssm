package com.sheng.tmall.service;

import com.sheng.tmall.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> list();
    void add(Category category);
    void update(Category category);
    void delete(int id);
    Category get(int id);
}
