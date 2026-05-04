package com.li.emp.controller;


import com.li.emp.pojo.Dept;
import com.li.emp.pojo.Result;
import com.li.emp.service.DeptService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//用来响应数据 控制层

@Slf4j
@RestController
public class DeptController {
    //
        //注入依赖
    @Autowired
    private DeptService deptService;
    //响应请求
    @GetMapping("/depts")
    public Result findAll(){
        log.debug("接受请求 findAll方法");
        System.out.println("DeptController.findAll");

        List<Dept> depts = deptService.findAll();

        return Result.success(depts);
    }

    @DeleteMapping ("/depts")
    public Result delete (@RequestParam(value = "id",required = false)Integer deptId){  //接受前端的参数 必须对应
        deptService.delete(deptId);

        System.out.println("删除第"+deptId);
        return Result.success();
    }

    //增加的
    @PostMapping("/depts")  //标签用来接受 post 请求
    public Result insert (@RequestBody Dept dept){
        System.out.println("增加部门");
        deptService.insert(dept);
        return Result.success();
    }
    //查询回显
    @GetMapping("/depts/{id}")
    public Result findById(@PathVariable(value = "id")Integer deptId){
        Dept dept = deptService.findById(deptId);
        return Result.success(dept);
    }

    @PutMapping("depts")
    public Result update(@RequestBody Dept dept){
        deptService.update(dept);
        return Result.success();
    }
}
