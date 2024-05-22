package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //如果请求为 OPTIONS 请求，则返回 true,否则需要通过jwt验证
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            System.out.println("OPTIONS请求，放行");
            return true;
        }

        System.out.println("preHandle: " + request.getRequestURI());
        // 从请求头中获取 Authorization 信息
//        String authorizationHeader = request.getHeader("Authorization");

//        // 检查 Authorization 是否为null或空字符串
//        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
//            // 如果 Authorization 不存在或为空，则拦截请求，并返回未授权状态码
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            System.out.println("postHandle: Authorization 不存在或为空，拦截请求 " + request.getRequestURI());
//            return false;
//        }
        // 提取 token
//        String token = authorizationHeader.substring(7); // 去掉 "Bearer " 前缀
//        //验证是否是vip            // 解析 token
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
            // 检查是否包含 VIP 属性
//            boolean isVip = claims.containsKey("vip") && (boolean) claims.get("vip");
            // 输出相应的提示信息
//            if (isVip) {
//                System.out.println("尊贵的 VIP 用户");
//                System.out.println("尊贵的 VIP 用户可以观看视频！");
//            } else {
//                System.out.println("穷鬼用户");
//                // 检查请求的 URI 是否匹配 /fruit/movieId/{dynamic}
//                if (request.getRequestURI().matches("/fruit/movieId/\\d+")) {
//                    System.out.println("识别到穷鬼！拦截穷鬼观看视频！");
//                    return false;
//                }
//            }

        //验证token是否有效性
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token);
//        } catch (JwtException e) {
//            // 如果 token 验证失败，则拦截请求，并返回未授权状态码
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            System.out.println("postHandle: token 验证失败，拦截请求 " + request.getRequestURI());
//            return false;
//        }
//        System.out.println("postHandle: token 验证成功，请求通过 " + request.getRequestURI());
//        // 如果 token 存在且有效，则允许请求继续向后端控制器传递
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("postHandle: " + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("afterCompletion: " + request.getRequestURI());
    }
}
