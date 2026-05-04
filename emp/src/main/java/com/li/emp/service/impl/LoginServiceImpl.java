package com.li.emp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.li.emp.mapper.EmpMapper;
import com.li.emp.pojo.EmpEntity;
import com.li.emp.pojo.LoginDTO;
import com.li.emp.service.LoginService;
import com.li.emp.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 登录服务实现类
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private EmpMapper empMapper;

    /**
     * 员工登录
     * @param loginDTO 登录信息（用户名和密码）
     * @return 包含员工信息和令牌的实体对象
     */
    @Override
    public EmpEntity login(LoginDTO loginDTO) {
        // 1. 校验参数
        if (!StringUtils.hasText(loginDTO.getUsername()) || !StringUtils.hasText(loginDTO.getPassword())) {
            throw new RuntimeException("用户名或密码不能为空");
        }

        // 2. 根据用户名查询员工信息
        QueryWrapper<EmpEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginDTO.getUsername());
        EmpEntity emp = empMapper.selectOne(wrapper);

        // 3. 判断用户是否存在
        if (emp == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 4. 校验密码（注意：生产环境应该对密码进行加密比较，如 BCrypt）
        if (!loginDTO.getPassword().equals(emp.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 5. 生成 JWT 令牌（包含 id 和 name）
        String token = JwtUtils.generateToken(emp.getId(), emp.getName());
        
        // 6. 将令牌赋值给当前员工对象，用于返回给前端
        emp.setToken(token);
        // 安全处理：返回给前端时不携带密码
        emp.setPassword(null);
        
        log.info("员工登录成功: {}, Token: {}", emp.getName(), token);
        
        return emp;
    }
}
