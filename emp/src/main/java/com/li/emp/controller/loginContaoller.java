package com.li.emp.controller;

import com.li.emp.pojo.EmpEntity;
import com.li.emp.pojo.LoginDTO;
import com.li.emp.pojo.Result;
import com.li.emp.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 */
@Slf4j
@RestController
public class loginContaoller {

    @Autowired
    private LoginService loginService;

    /**
     * 员工登录
     * @param loginDTO 登录信息（用户名和密码）
     * @return JWT 令牌
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        log.info("员工登录: {}", loginDTO.getUsername());
        
        try {
            // 调用登录服务，获取包含 Token 的员工对象
            EmpEntity emp = loginService.login(loginDTO);
            return Result.success(emp);
        } catch (RuntimeException e) {
            log.error("登录失败: {}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
