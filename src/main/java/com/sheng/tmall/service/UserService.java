package com.sheng.tmall.service;

import com.sheng.tmall.pojo.User;

import java.util.List;

public interface UserService {
    List<User> list();
    void add(User bean);
    void update(User bean);
    void delete(int id);
    User get(int id);

    //fore add
    boolean isExist(String name);

    User get(String name, String password);
}
