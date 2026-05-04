package com.li.emp.utils;

/**
 * 用户上下文工具类
 * 基于 ThreadLocal 存储当前登录用户信息
 */
public class UserContext {

    // ThreadLocal 变量,每个线程独立存储
    private static final ThreadLocal<Integer> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_NAME = new ThreadLocal<>();

    /**
     * 设置当前用户 ID
     */
    public static void setUserId(Integer userId) {
        USER_ID.set(userId);
    }

    /**
     * 获取当前用户 ID
     */
    public static Integer getUserId() {
        return USER_ID.get();
    }

    /**
     * 设置当前用户姓名
     */
    public static void setUserName(String userName) {
        USER_NAME.set(userName);
    }

    /**
     * 获取当前用户姓名
     */
    public static String getUserName() {
        return USER_NAME.get();
    }

    /**
     * 清除当前线程的用户信息(防止内存泄漏)
     */
    public static void clear() {
        USER_ID.remove();
        USER_NAME.remove();
    }
}
