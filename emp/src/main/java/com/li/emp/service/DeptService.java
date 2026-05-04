package com.li.emp.service;

import com.li.emp.pojo.Dept;

import java.util.List;

public interface DeptService {

    public List<Dept> findAll();

    void delete(Integer deptId);

    void insert(Dept dept);

    Dept findById(Integer deptId);

    void update(Dept dept);
}
