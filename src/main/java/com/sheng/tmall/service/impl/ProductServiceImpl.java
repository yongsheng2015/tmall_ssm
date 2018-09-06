package com.sheng.tmall.service.impl;

import com.sheng.tmall.mapper.ProductMapper;
import com.sheng.tmall.pojo.*;
import com.sheng.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    /**
     *
     */
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderItemService orderItemService;

    @Override
    public void add(Product bean) {
        productMapper.insert(bean);
    }

    @Override
    public void update(Product bean) {
        productMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public boolean delete(int id) {
        try {
            productMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Product get(int id) {
        Product p=productMapper.selectByPrimaryKey(id);
        setCategory(p);
        setFirstProductImage(p);
        return p;
    }

    @Override
    public List<Product> list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> result = productMapper.selectByExample(example);
        setCategory(result);
        setFirstProductImage(result);
        return result;
    }

    public void setCategory(List<Product> beans) {
        for (Product p:beans)
            setCategory(p);
    }

    public void setCategory(Product bean) {
        int cid = bean.getCid();
        Category category = categoryService.get(cid);
        bean.setCategory(category);
    }

    @Override
    public void setFirstProductImage(Product bean) {
        List<ProductImage> pis = productImageService.list(bean.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            bean.setFirstProductImage(pis.get(0));
        }
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }

    @Override
    public void fill(List<Category> cs) {
        for (Category c : cs) {
            fill(c);
        }
    }

    @Override
    public void fill(Category c) {
        List<Product> products = list(c.getId());
        c.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> cs) {
        int productNumberEachRow = 8;
        for (Category c : cs) {
            List<Product> products = c.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for(int i=0;i<products.size();i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size=size>products.size()?products.size():size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product p) {
        int saleCount = orderItemService.getSaleCount(p.getId());
        int reviewCount = reviewService.getCount(p.getId());
        p.setSaleCount(saleCount);
        p.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");

        List<Product> result = productMapper.selectByExample(example);
        setFirstProductImage(result);
        setCategory(result);
        return result;
    }
}
