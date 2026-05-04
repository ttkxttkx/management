package com.li.emp.interceptor;

import com.li.emp.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT 令牌拦截器
 * 用于校验请求中的 JWT 令牌
 */
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    /**
     * 在请求处理之前进行拦截校验
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  被调用的处理器
     * @return true 放行，false 拦截
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求 URL
        String url = request.getRequestURI();
        log.info("请求的 URL: {}", url);

        // 判断是否为登录请求，如果是则直接放行
        if (url.contains("/login")) {
            log.info("登录请求，放行");
            return true;
        }

        // 获取请求头中的令牌
        String token = request.getHeader("token");

        // 判断令牌是否存在
        if (!StringUtils.hasLength(token)) {
            log.info("请求头中 token 为空，返回 401");
            response.setStatus(401);
            return false;
        }

        // 解析令牌
        try {
            JwtUtils.parseToken(token);
            log.info("令牌合法，放行");
            return true;
        } catch (Exception e) {
            log.info("令牌解析失败: {}", e.getMessage());
            response.setStatus(401);
            return false;
        }
    }
}
