package com.li.emp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * 用于生成和解析 JWT 令牌
 */
@Slf4j
public class JwtUtils {

    /**
     * 签名密钥（生产环境请改为更复杂的字符串，或从配置文件读取）
     */
    private static final String SECRET_KEY = "tlias_secret_key_2024";

    /**
     * 令牌过期时间：15 分钟（单位：毫秒）
     */
    private static final long EXPIRATION_TIME = 15 * 60 * 1000L;

    /**
     * 生成 JWT 令牌
     * 
     * @param id   员工 ID（整型）
     * @param name 员工姓名（字符串）
     * @return JWT 字符串
     */
    public static String generateToken(Integer id, String name) {
        // 构建自定义声明（Payload）
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("name", name);

        // 生成 Token
        return Jwts.builder()
                .setClaims(claims)                    // 自定义信息
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 签名算法
                .setIssuedAt(new Date())              // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .compact();
    }

    /**
     * 解析 JWT 令牌
     * 
     * @param token JWT 字符串
     * @return Claims 对象（包含自定义信息）
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT 解析失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从 Token 中获取员工 ID
     * 
     * @param token JWT 字符串
     * @return 员工 ID
     */
    public static Integer getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.get("id", Integer.class);
        }
        return null;
    }

    /**
     * 从 Token 中获取员工姓名
     * 
     * @param token JWT 字符串
     * @return 员工姓名
     */
    public static String getUserName(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.get("name", String.class);
        }
        return null;
    }
}
