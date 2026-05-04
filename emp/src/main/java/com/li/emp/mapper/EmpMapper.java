package com.li.emp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.emp.pojo.EmpEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工数据访问层接口
 * 继承 MyBatis-Plus 的 BaseMapper，自动获得 CRUD 方法
 */
@Mapper
public interface EmpMapper extends BaseMapper<EmpEntity> {
    // BaseMapper 已经提供了 insert、deleteById、updateById、selectById 等常用方法
}
