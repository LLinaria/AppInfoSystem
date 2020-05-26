package com.jbit.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SysInterceptor implements HandlerInterceptor {
    /**
     * 业务方法请求前，执行
     * @param request
     * @param response
     * @param handler
     * @return true 放行  false 拦截
     * @throws Exception
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录请求放行
        HttpSession session = request.getSession();
        Object devuser = session.getAttribute("devuser");
        if(devuser != null){
            return true;
        }
        //如果没有登录重定向到登录页面（login）
        response.sendRedirect("/jsp/devlogin.jsp");
        return false;
    }
}
