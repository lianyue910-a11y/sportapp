package com.lianyue.Util;

import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;

public class Md5 {
    // 盐值：就像给密码加点佐料，防止黑客反向破解
    private static final String SALT = "gdut@sport#2026";
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
