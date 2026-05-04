package com.li.emp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.li.emp.dto.EmpDTO;
import com.li.emp.pojo.EmpEntity;

import java.util.List;

/**
 * 员工服务接口
 */
public interface EmpService {
    
    /**
     * 分页条件查询员工信息
     * @param empPageQueryDTO 查询条件
     * @return 分页结果
     */
    IPage<EmpEntity> page(EmpDTO empPageQueryDTO);
    
    /**
     * 添加员工
     * @param empEntity 员工信息
     */
    void save(EmpEntity empEntity);
    
    /**
     * 批量删除员工
     * @param ids 员工ID数组
     */
    void deleteBatch(List<Integer> ids);
    
    /**
     * 根据ID查询员工信息
     * @param id 员工ID
     * @return 员工信息
     */
    EmpEntity getById(Integer id);
    
    /**
     * 修改员工信息（只更新非空字段）
     * @param empEntity 员工信息（必须包含 id）
     */
    void update(EmpEntity empEntity);
    
    /**
     * 查询全部员工信息
     * @return 员工列表
     */
    List<EmpEntity> list();
}
