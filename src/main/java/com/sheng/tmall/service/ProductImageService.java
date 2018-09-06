package com.sheng.tmall.service;

import com.sheng.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {
    String type_single = "type_single";
    String type_detail = "type_detail";

    List<ProductImage> list(int pid,String type);
    void add(ProductImage productImage);
    void update(ProductImage productImage);
    void delete(int id);
    ProductImage get(int id);
}
