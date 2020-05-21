package com.jbit.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backend") // 区分前缀
public class BackUserController {

    @PostMapping("login")
    public String login(){
        return "";
    }
}
