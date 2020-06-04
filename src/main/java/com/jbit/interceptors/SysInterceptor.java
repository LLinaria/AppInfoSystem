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
        // 目前 session dev（userdev） backend（backuser）
        // System.out.println(request.getMethod()); // GET/POST
        // System.out.println(request.getRequestURI()); // /dev/app/list
        // System.out.println(request.getRequestURL()); // http://localhost:8080/dev/app/list
        // 登录请求放行
        HttpSession session = request.getSession();
        Object devuser = session.getAttribute("devuser");
        Object backuser = session.getAttribute("backuser");
        if(devuser!=null || backuser!=null){
            return true;
        }
        response.sendRedirect("/index.jsp");
//        if(request.getRequestURI().startsWith("/dev")){
//            Object devuser = session.getAttribute("devuser");
//            if(devuser != null){
//                return true;
//            }
//            //如果没有登录重定向到登录页面（login）
//            response.sendRedirect("/jsp/devlogin.jsp");
//        }else if(request.getRequestURI().startsWith("/backend")){
//            Object backuser = session.getAttribute("backuser");
//            if(backuser != null){
//                return true;
//            }
//            //如果没有登录重定向到登录页面（login）
//            response.sendRedirect("/jsp/backendlogin.jsp");
//        }else {
//            Object devuser = session.getAttribute("devuser");
//            Object backuser = session.getAttribute("backuser");
//            if(devuser!=null || backuser!=null){
//                return true;
//            }
//        }
        return false;
    }
}
