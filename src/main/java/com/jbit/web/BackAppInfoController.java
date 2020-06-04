package com.jbit.web;

import com.jbit.pojo.AppInfo;
import com.jbit.service.AppCategoryService;
import com.jbit.service.AppInfoService;
import com.jbit.service.AppVersionService;
import com.jbit.service.DataDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("backend/app")
public class BackAppInfoController {

    @Resource
    private AppInfoService appInfoService;

    @Resource
    private DataDictionaryService dataDictionaryService;

    @Resource
    private AppCategoryService appCategoryService;

    @Resource
    private AppVersionService appVersionService;

    /**
     * 实现审核
     * @param appinfo
     * @return
     */
    @PostMapping("checksave")
    public String checksave(AppInfo appinfo){
        appInfoService.update(appinfo);
        return "redirect:/backend/app/list";
    }

    /**
     * 跳转更新版本信息页面
     * @param model
     * @param aid
     * @param vid
     * @return
     */
    @GetMapping("check")
    public String appversionmodify(Model model, Long aid, Long vid) {
        model.addAttribute("appInfo", appInfoService.queryByID(vid));
        model.addAttribute("appVersion",appVersionService.queryById(aid));
        return "backend/appcheck";
    }

    /**
     * App后台审核信息列表
     * @param session
     * @param model
     * @param pageIndex
     * @param querySoftwareName
     * @param queryFlatformId
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     * @return
     */
    @RequestMapping("list")
    public String list(HttpSession session, Model model, @RequestParam(defaultValue = "1") Integer pageIndex, String querySoftwareName, Long queryFlatformId, Long queryCategoryLevel1, Long queryCategoryLevel2, Long queryCategoryLevel3){
        // 防止 session丢失
        model.addAttribute("appInfoList",appInfoService.queryAppInfo(pageIndex,null,querySoftwareName,1L,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3));
        // 绑定 所属平台
        model.addAttribute("flatFormList",dataDictionaryService.queryDataByType("APP_FLATFORM"));
        // 绑定 一级分类
        model.addAttribute("categoryLevel1List",appCategoryService.queryByParentId(null));
        // 处理 二级分类
        if(queryCategoryLevel1!=null){
            model.addAttribute("categoryLevel2List",appCategoryService.queryByParentId(queryCategoryLevel1));
        }
        // 处理 三级分类
        if(queryCategoryLevel2!=null){
            model.addAttribute("categoryLevel3List",appCategoryService.queryByParentId(queryCategoryLevel2));
        }
        // 参数重新封装传回到页面
        model.addAttribute("querySoftwareName",querySoftwareName);
        model.addAttribute("queryFlatformId",queryFlatformId);
        model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);
        model.addAttribute("queryCategoryLevel2",queryCategoryLevel2);
        model.addAttribute("queryCategoryLevel3",queryCategoryLevel3);
        return "backend/applist";
    }
}
