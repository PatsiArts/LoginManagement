package com.patsi.interceptors;

import com.patsi.annotations.RequireIPAddress;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class IPInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequireIPAddress annotation = handlerMethod.getMethod().getAnnotation(RequireIPAddress.class);
            if (annotation != null) {
                String clientIp = request.getRemoteAddr();
                request.setAttribute("clientIp", clientIp);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Missing or invalid clientIp");
                return false;
            }
        }
        return true;
    }
}
