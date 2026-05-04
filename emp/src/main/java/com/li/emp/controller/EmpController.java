package com.li.emp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.li.emp.dto.EmpDTO;
import com.li.emp.pojo.EmpEntity;
import com.li.emp.pojo.Result;
import com.li.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理控制器
 */
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 添加员工
     * @param empEntity 员工信息（除id以外的所有属性）
     * @return 统一结果封装
     */
    @PostMapping
    public Result save(@RequestBody EmpEntity empEntity) {
        empService.save(empEntity);
        return Result.success();
    }

    /**
     * 批量删除员工
     * @param ids 员工ID数组
     * @return 统一结果封装
     */
    @DeleteMapping
    public Result deleteBatch(@RequestParam("ids") List<Integer> ids) {
        empService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据ID查询员工信息（回显）
     * @param id 员工ID
     * @return 员工信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        EmpEntity emp = empService.getById(id);
        return Result.success(emp);
    }

    /**
     * 修改员工信息
     * @param empEntity 员工信息（必须包含 id 和需要修改的字段）
     * @return 统一结果封装
     */
    @PutMapping
    public Result update(@RequestBody EmpEntity empEntity) {
        empService.update(empEntity);
        return Result.success();
    }

    /**
     * 分页条件查询员工信息（路径：/emps）
     * @param empPageQueryDTO 查询条件（包含页码、每页条数、姓名、性别等）
     * @return 分页结果
     */
    @GetMapping
    public Result page(EmpDTO empPageQueryDTO) {
        IPage<EmpEntity> page = empService.page(empPageQueryDTO);
        
        // MyBatis-Plus 默认返回 records，前端通常需要 list 或 rows
        // 为了兼容前端，这里做一下字段映射
        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("rows", page.getRecords()); // 适配 Element UI 等常见前端框架
        result.put("list", page.getRecords()); 
        
        return Result.success(result);
    }

    /**
     * 查询全部员工信息（路径：/emps/list）
     * @return 员工列表
     */
    @GetMapping("/list")
    public Result list() {
        List<EmpEntity> list = empService.list();
        return Result.success(list);
    }
}
