package com.li.emp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.li.emp.dto.EmpDTO;
import com.li.emp.mapper.EmpMapper;
import com.li.emp.pojo.EmpEntity;
import com.li.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 员工服务实现类
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    /**
     * 分页条件查询员工信息
     * @param empPageQueryDTO 查询条件
     * @return 分页结果
     */
    @Override
    public IPage<EmpEntity> page(EmpDTO empPageQueryDTO) {
        // 增加健壮性处理，防止前端未传分页参数导致异常
        int currentPage = (empPageQueryDTO.getPage() == null) ? 1 : empPageQueryDTO.getPage();
        int pageSize = (empPageQueryDTO.getPageSize() == null) ? 10 : empPageQueryDTO.getPageSize();
        
        // 构建分页对象
        Page<EmpEntity> page = new Page<>(currentPage, pageSize);
        
        // 构建查询条件（使用 QueryWrapper 语义更准确）
        QueryWrapper<EmpEntity> wrapper = new QueryWrapper<>();
        
        // 姓名模糊匹配
        if (StringUtils.hasText(empPageQueryDTO.getName())) {
            wrapper.like("name", empPageQueryDTO.getName());
        }
        
        // 性别筛选
        if (empPageQueryDTO.getGender() != null) {
            wrapper.eq("gender", empPageQueryDTO.getGender());
        }
        
        // 执行分页查询
        return empMapper.selectPage(page, wrapper);
    }

    /**
     * 添加员工
     * @param empEntity 员工信息
     */
    @Override
    @Transactional
    public void save(EmpEntity empEntity) {
        // MyBatis-Plus 自动处理主键自增和时间字段自动填充
        empMapper.insert(empEntity);
    }

    /**
     * 批量删除员工
     * @param ids 员工ID数组
     */
    @Override
    @Transactional
    public void deleteBatch(List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            empMapper.deleteBatchIds(ids);
        }
    }

    /**
     * 根据ID查询员工信息
     * @param id 员工ID
     * @return 员工信息
     */
    @Override
    public EmpEntity getById(Integer id) {
        return empMapper.selectById(id);
    }

    /**
     * 修改员工信息（只更新非空字段）
     * @param empEntity 员工信息（必须包含 id）
     */
    @Override
    @Transactional
    public void update(EmpEntity empEntity) {
        // 使用 UpdateWrapper 只更新非空字段
        UpdateWrapper<EmpEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", empEntity.getId());
        
        // 只设置非空字段（排除 id）
        if (empEntity.getUsername() != null) {
            wrapper.set("username", empEntity.getUsername());
        }
        if (empEntity.getPassword() != null) {
            wrapper.set("password", empEntity.getPassword());
        }
        if (empEntity.getName() != null) {
            wrapper.set("name", empEntity.getName());
        }
        if (empEntity.getGender() != null) {
            wrapper.set("gender", empEntity.getGender());
        }
        if (empEntity.getPhone() != null) {
            wrapper.set("phone", empEntity.getPhone());
        }
        if (empEntity.getJob() != null) {
            wrapper.set("job", empEntity.getJob());
        }
        if (empEntity.getSalary() != null) {
            wrapper.set("salary", empEntity.getSalary());
        }
        if (empEntity.getImage() != null) {
            wrapper.set("image", empEntity.getImage());
        }
        if (empEntity.getEntryDate() != null) {
            wrapper.set("entry_date", empEntity.getEntryDate());
        }
        if (empEntity.getDeptId() != null) {
            wrapper.set("dept_id", empEntity.getDeptId());
        }
        
        empMapper.update(null, wrapper);
    }

    /**
     * 查询全部员工信息
     * @return 员工列表
     */
    @Override
    public List<EmpEntity> list() {
        return empMapper.selectList(null);
    }
}
