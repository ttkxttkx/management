package com.li.emp.mapper;


import com.li.emp.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//接口 用来实现数据访问
@Mapper
public interface DeptMapper {

    //查询所有部门

    public List<Dept> findAll();

    void delete(@Param("id") Integer id);

    void insert(Dept dept);

    Dept getById(Integer id);

    void update(Dept dept);
}
