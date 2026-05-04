package com.li.emp.filter;

import com.li.emp.utils.JwtUtils;
import com.li.emp.utils.UserContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 校验过滤器
 * 拦截所有请求，校验 Token 合法性
 */
@Slf4j
@WebFilter(filterName = "JwtFilter", urlPatterns = "/*")
public class filter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化时执行（通常不需要处理）
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            // 1. 获取请求路径
            String url = request.getRequestURI();
            log.info("拦截到请求: {}", url);
            // 2. 判断是否是登录请求（登录请求不需要校验 Token）
            if (url.contains("/login")) {
                log.info("登录请求，放行");
                filterChain.doFilter(request, response);
                return;
            }

            // 3. 获取请求头中的 Token
            String token = request.getHeader("token");

            // 4. 校验 Token 是否存在（业务异常：返回 401）
            if (token == null || token.isEmpty()) {
                log.warn("请求路径: {}, Token 为空，拦截", url);
                notLogin(response);
                return;
            }

            // 5. 校验 Token 是否合法（业务异常：返回 401）
            Claims claims = JwtUtils.parseToken(token);
            if (claims == null) {
                log.warn("请求路径: {}, Token 解析失败，拦截", url);
                notLogin(response);
                return;
            }
            
            // 将用户信息存入 ThreadLocal
            Integer userId = claims.get("id", Integer.class);
            String userName = claims.get("name", String.class);
            UserContext.setUserId(userId);
            UserContext.setUserName(userName);
            log.info("Token 校验通过，用户 ID: {}, 姓名: {}", userId, userName);

            // 6. Token 有效，放行请求
            filterChain.doFilter(request, response);
            
        } catch (IOException e) {
            // IO 异常直接抛出（框架会处理）
            throw e;
        } catch (ServletException e) {
            // Servlet 异常直接抛出
            throw e;
        } catch (Exception e) {
            // 未知异常：记录日志 + 返回 500
            log.error("Filter 执行异常: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            try {
                response.getWriter().write("{\"code\":0,\"msg\":\"系统异常,请联系管理员\"}");
            } catch (IOException ex) {
                log.error("响应写入失败", ex);
            }
        } finally {
            // 清除 ThreadLocal，防止内存泄漏
            UserContext.clear();
        }
    }

    @Override
    public void destroy() {
        // 过滤器销毁时执行（通常不需要处理）
    }

    /**
     * 响应未登录/Token 失效的 JSON 数据
     */
    private void notLogin(HttpServletResponse response) throws IOException {
        // 设置 HTTP 状态码为 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        // 返回 JSON 格式的错误信息
        response.getWriter().write("{\"code\":0,\"msg\":\"NOT_LOGIN\"}");
    }
}
