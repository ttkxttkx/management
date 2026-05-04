package com.li.emp.pojo;

import lombok.Data;

/**
 * 登录请求 DTO
 */
@Data
public class LoginDTO {
    
    private String username; // 用户名
    
    private String password; // 密码
}
