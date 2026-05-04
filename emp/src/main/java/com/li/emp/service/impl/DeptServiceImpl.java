package com.li.emp.service.impl;

import com.li.emp.mapper.DeptMapper;
import com.li.emp.pojo.Dept;
import com.li.emp.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll (){
        return deptMapper.findAll();
    }

    @Override
    public void delete(Integer id){
        deptMapper.delete(id);
    }
    @Override
    public void insert(Dept dept){
        dept.setCreateTime(LocalDateTime.now());  //补全时间 因为 json 语句内没有关于时间的描述
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    @Override
    public Dept findById(Integer id){
        deptMapper.getById(id);
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept){
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }

}
