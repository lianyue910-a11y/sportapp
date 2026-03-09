package com.lianyue.interceptor;

import com.lianyue.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 处理 OPTIONS 预检请求 (前端跨域时会先发这个，必须放行)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 2. 从 Header 中获取 token,前端约定 header 里的 key 叫 "token"
        String token = request.getHeader("token");
        if (token != null && !token.isEmpty()) {
            try {
                // 3. 尝试解析 token
                Claims claims = JwtUtil.parseToken(token);
                // 4. 解析成功！把 userId 取出来，存到 request 域里
                Integer userId = (Integer) claims.get("userId");
                String role = (String) claims.get("role");
                request.setAttribute("userId", userId);
                request.setAttribute("role", role);
                return true; //放行
            } catch (Exception e) {
                // 解析失败 (过期或伪造)
                System.out.println("Token 无效: " + e.getMessage());
            }
        }

        // 5. 拦截：返回 401 状态码和 JSON 提示
        response.setStatus(401);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"code\":401, \"msg\":\"未登录或Token已过期\"}");
        return false;
    }
}