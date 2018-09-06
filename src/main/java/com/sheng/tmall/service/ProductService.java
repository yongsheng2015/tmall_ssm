package com.sheng.tmall.service;

import com.sheng.tmall.pojo.Category;
import com.sheng.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> list(int cid);
    void add(Product bean);
    void update(Product bean);
    boolean delete(int id);
    Product get(int id);

    //admin add
    void setFirstProductImage(Product bean);

    //fore add
    void fill(List<Category> cs);

    void fill(Category c);

    void fillByRow(List<Category> cs);

    void setSaleAndReviewNumber(Product p);

    void setSaleAndReviewNumber(List<Product> ps);

    List<Product> search(String keyword);
}
