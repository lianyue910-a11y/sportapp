package com.lianyue.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    //秘钥
    private static final String SECRET_KEY = "LianYue@Sport#2026";

    //过期时间：12小时 (毫秒单位)
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成 Token
     * @param userId 用户ID
     * @param role 用户角色 (student/teacher/admin)
     * @return 加密后的字符串
     */
    public static String createToken(Integer userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims) // 放入自定义数据
                .setSubject("LoginUser") // 主题
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 签名算法
                .compact();
    }
    /**
     * 解析 Token
     * @param token 前端传来的字符串
     * @return Claims 对象 (里面包含了 userId 和 role)
     * @throws Exception 如果过期或被篡改，会抛异常
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}