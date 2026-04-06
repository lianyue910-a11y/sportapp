package com.lianyue.config;

import com.lianyue.Util.JwtUtil;
import com.lianyue.Util.Md5;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 安全配置类，用于初始化安全相关的配置
 */
@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${jwt.expire-hours:12}")
    private Long jwtExpireHours;

    @Value("${password.salt:gdut@sport#2026}")
    private String passwordSalt;

    /**
     * 应用启动后初始化JWT工具类的配置
     */
    @PostConstruct
    public void initSecurityConfig() {
        // 初始化JWT配置
        boolean jwtKeyConfigured = false;
        if (jwtSecretKey != null && !jwtSecretKey.trim().isEmpty()) {
            JwtUtil.setSecretKey(jwtSecretKey);
            jwtKeyConfigured = true;
            log.info("JWT密钥已从配置加载（长度：{}）", jwtSecretKey.length());
        } else {
            log.warn("⚠️ JWT密钥未配置，使用默认开发密钥！生产环境必须设置JWT_SECRET_KEY环境变量");
        }

        if (jwtExpireHours != null) {
            JwtUtil.setExpireTime(jwtExpireHours * 60 * 60 * 1000);
            log.info("JWT过期时间设置为：{}小时", jwtExpireHours);
        }

        // 初始化密码加密盐值
        if (passwordSalt != null && !passwordSalt.trim().isEmpty()) {
            Md5.setSalt(passwordSalt);
            log.info("密码加密盐值已从配置加载（长度：{}）", passwordSalt.length());
        } else {
            log.warn("⚠️ 密码加密盐值未配置，使用默认值！生产环境建议设置PASSWORD_SALT环境变量");
        }

        // 验证配置（生产环境必须配置JWT密钥）
        if (!jwtKeyConfigured && isProductionEnvironment()) {
            throw new IllegalStateException("生产环境必须配置JWT_SECRET_KEY环境变量");
        }
    }

    /**
     * 判断是否为生产环境
     * @return true如果是生产环境
     */
    private boolean isProductionEnvironment() {
        String env = System.getenv("SPRING_PROFILES_ACTIVE");
        return "prod".equals(env) || "production".equals(env);
    }
}