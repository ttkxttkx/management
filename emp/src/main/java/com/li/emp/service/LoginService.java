package com.li.emp.service;

import com.li.emp.pojo.EmpEntity;
import com.li.emp.pojo.LoginDTO;

/**
 * 登录服务接口
 */
public interface LoginService {
    
    /**
     * 员工登录
     * @param loginDTO 登录信息（用户名和密码）
     * @return 包含员工信息和令牌的实体对象
     */
    EmpEntity login(LoginDTO loginDTO);
}
