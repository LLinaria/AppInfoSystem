package com.jbit.web;

import com.jbit.pojo.BackendUser;
import com.jbit.service.BackendUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("backend") // 区分前缀
public class BackUserController {

    @Resource
    private BackendUserService backendUserService;

    /**
     * 后台登录
     * @param session
     * @param usercode
     * @param userpassword
     * @return
     */
    @PostMapping("login")
    public String login(HttpSession session, String usercode, String userpassword){
        BackendUser backendUser = backendUserService.queryLogin(usercode, userpassword);
        if (backendUser!=null) {
            session.setAttribute("backuser",backendUser);
            return "redirect:/jsp/backend/main.jsp";
        }
        return "backendlogin";
    }

    /**
     * 后台用户退出
     * @param session
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/jsp/backendlogin.jsp"; // 重定向
    }
}
