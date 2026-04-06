package com.lianyue.Util;

import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;

public class Md5 {
    // 盐值：就像给密码加点佐料
    private static volatile String SALT = "gdut@sport#2026";

    /**
     * 设置盐值（由SecurityConfig在应用启动时调用）
     * @param salt 盐值
     */
    public static synchronized void setSalt(String salt) {
        if (salt != null && !salt.trim().isEmpty()) {
            SALT = salt;
        }
    }

    /**
     * 获取当前盐值（用于调试或验证）
     * @return 当前盐值
     */
    public static String getSalt() {
        return SALT;
    }

    public static String encrypt(String password) {
        if (password == null) return null;
        // 1. 拼接：密码 + 盐
        String base = password + SALT;
        // 2. 循环加密几次 (为了更安全)
        for (int i = 0; i < 3; i++) {
            base = DigestUtils.md5DigestAsHex(base.getBytes(StandardCharsets.UTF_8));
        }
        return base;
    }
}
