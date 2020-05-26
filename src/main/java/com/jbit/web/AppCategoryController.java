package com.jbit.web;

import com.alibaba.fastjson.JSONArray;
import com.jbit.pojo.AppCategory;
import com.jbit.service.AppCategoryService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class AppCategoryController {

    @Resource
    private AppCategoryService appCategoryService;

    @GetMapping("categorylevellist")
    @ResponseBody
    public List<AppCategory> queryByPid(Long pid){
        return appCategoryService.queryByParentId(pid);
    }
}
