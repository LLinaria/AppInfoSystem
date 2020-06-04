package com.jbit.web;

import com.jbit.entity.JsonResult;
import com.jbit.pojo.AppInfo;
import com.jbit.pojo.AppVersion;
import com.jbit.pojo.DevUser;
import com.jbit.service.AppCategoryService;
import com.jbit.service.AppInfoService;
import com.jbit.service.AppVersionService;
import com.jbit.service.DataDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("dev/app")
public class AppInfoController {
    @Resource
    private AppInfoService appInfoService;

    @Resource
    private DataDictionaryService dataDictionaryService;

    @Resource
    private AppCategoryService appCategoryService;

    @Resource
    private AppVersionService appVersionService;

    @PutMapping("{id}/sale")
    @ResponseBody
    public JsonResult delap(@PathVariable Long id){
        AppInfo appInfo = appInfoService.queryById(id);
        if(appInfo.getStatus() == 2 || appInfo.getStatus() == 5){
            appInfo.setStatus(4L);
            appInfoService.update(appInfo);
            return new JsonResult(true);
        }else if(appInfo.getStatus() == 4){
            appInfo.setStatus(5L);
            appInfoService.update(appInfo);
            return new JsonResult(true);
        }else {
            return new JsonResult(false);
        }
    }

    /**
     * 查看页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("appview/{id}")
    public String appview(Model model, @PathVariable Long id){
        model.addAttribute("appInfo",appInfoService.queryByID(id));
        model.addAttribute("appVersionList",appVersionService.queryByAppId(id));
        return "developer/appinfoview";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("delapp")
    @ResponseBody
    public JsonResult delapp(Long id){
        int i = appInfoService.delete(id);
        if (i > 0){
            appVersionService.deleteByAppId(id);
            return new JsonResult(true);
        }
        return new JsonResult(false);
    }

    /**
     * 新增 显示 App 版本信息
     * @param model
     * @param appid
     * @return
     */
    @GetMapping("appversionadd/{appid}")
    public String appversionadd(Model model, @PathVariable Long appid) {
        model.addAttribute("appVersionList", appVersionService.queryByAppId(appid));
        model.addAttribute("appVersionId",appid);
        return "developer/appversionadd";
    }

    /**
     * 修改 显示 APP 版本信息
     * @param model
     * @param id
     * @param appid
     * @return
     */
    @GetMapping("appversionmodify/{id}/{appid}")
    public String appversionmodify(Model model,@PathVariable Long id, @PathVariable Long appid) {
        model.addAttribute("appVersionList", appVersionService.queryByAppId(appid));
        model.addAttribute("appVersion",appVersionService.queryById(id));
        return "developer/appversionmodify";
    }

    /**
     * 新增 App 版本信息
     * @param session
     * @param appVersion
     * @param a_downloadLink
     * @return
     */
    @PostMapping("addappversion")
    public String addversion(HttpSession session, AppVersion appVersion, MultipartFile a_downloadLink) {
        // 1.实现文件上传
        String server_path = session.getServletContext().getRealPath("/statics/uploadfiles/");
        // 验证大小和图片规格 [未写]
        try {
            a_downloadLink.transferTo(new File(server_path,a_downloadLink.getOriginalFilename()));
        } catch (IOException e) {
        }
        DevUser devuser = (DevUser) session.getAttribute("devuser");
        appVersion.setCreatedby(devuser.getId());
        appVersion.setCreationdate(new Date());
        appVersion.setModifydate(new Date());
        appVersion.setDownloadlink("/statics/uploadfiles/"+a_downloadLink.getOriginalFilename()); // 相对路径
        appVersion.setApklocpath(server_path+a_downloadLink .getOriginalFilename());
        appVersion.setApkfilename(a_downloadLink.getOriginalFilename());
        appVersionService.save(appVersion);
        AppInfo appInfo = new AppInfo();
        appInfo.setId(appVersion.getAppid());
        appInfo.setVersionid(appVersion.getId());
        appInfoService.update(appInfo);
        return "redirect:/dev/app/appversionadd/"+appVersion.getAppid();
        // return "redirect:/dev/app/list";
    }

    /**
     * 修改 App 版本信息
     * @param session
     * @param appVersion
     * @param attach
     * @return
     */
    @PostMapping("appversionmodify")
    public String appversionmodify(HttpSession session, AppVersion appVersion, MultipartFile attach) {
        if(!attach.isEmpty()){ // null = true notnull = false
            // 1.实现文件上传
            String server_path = session.getServletContext().getRealPath("/statics/uploadfiles/");
            // 验证大小和图片规格 [未写]
            try {
                attach.transferTo(new File(server_path,attach.getOriginalFilename()));
                appVersion.setDownloadlink("/statics/uploadfiles/"+attach.getOriginalFilename()); // 相对路径
                appVersion.setApklocpath(server_path+attach .getOriginalFilename());
                appVersion.setApkfilename(attach.getOriginalFilename());
            } catch (IOException e) {
            }
        }
        DevUser devuser = (DevUser) session.getAttribute("devuser");
        appVersion.setModifyby(devuser.getId());
        appVersion.setModifydate(new Date());
        appVersionService.update(appVersion);
        return "redirect:/dev/app/list";
    }

    /**
     * 删除 logo 图片
     * 删除 apk 文件
     * @param id
     * @param flag
     * @return
     */
    @GetMapping("delfile")
    @ResponseBody
    public JsonResult delfile(Long id, String flag){
        if (flag.equals("logo")){ // 删除 logo
            AppInfo appInfo = appInfoService.queryById(id);
            // 通过绝对路径删除图片
            try {
                File file = new File(appInfo.getLogolocpath());
                file.delete(); // 删除文件
                appInfo.setLogopicpath("");
                appInfo.setLogolocpath("");
                appInfoService.update(appInfo);
                return new JsonResult(true);
            } catch (Exception e) {
                return new JsonResult(false);
            }
        }else if (flag.equals("apk")){ // 删除 apk
            AppVersion appVersion = appVersionService.queryById(id);
            // 通过绝对路径删除
            try {
                File file = new File(appVersion.getApklocpath());
                file.delete(); // 删除文件
                appVersion.setDownloadlink("");
                appVersion.setApklocpath("");
                appVersion.setApkfilename("");
                appVersionService.update(appVersion);
                return new JsonResult(true);
            } catch (Exception e) {
                return new JsonResult(false);
            }
        }
        return new JsonResult(false);
    }

    /**
     * 修改查询
     * @param model
     * @param id
     * @return
     */
    @GetMapping("appinfomodify/{id}")
    public String appinfomodify(Model model, @PathVariable Long id){
        model.addAttribute("appInfo",appInfoService.queryById(id));
        return "developer/appinfomodify";
    }

    /**
     * 验证 apk 是否存在注册
     * @param apkname
     * @return
     */
    @GetMapping("/apkexist")
    @ResponseBody
    public JsonResult apkexist(String apkname){
        AppInfo appInfo = appInfoService.queryApkexist(apkname);
        if (appInfo == null){
            return new JsonResult(true);
        }
        return new JsonResult(false);
    }

    /**
     * APP 新增
     * @param appinfo
     * @param a_logoPicPath
     * @return
     */
    @PostMapping("appinfoadd")
    public String appinfoadd(HttpSession session, AppInfo appinfo, MultipartFile a_logoPicPath){
        // 1.实现文件上传
        String server_path = session.getServletContext().getRealPath("/statics/uploadfiles/");
        // 验证大小和图片规格 [未写]
        try {
            a_logoPicPath.transferTo(new File(server_path,a_logoPicPath.getOriginalFilename()));
        } catch (IOException e) {
        }
        // 2.app添加
        DevUser devuser = (DevUser) session.getAttribute("devuser");
        appinfo.setDevid(devuser.getId());
        appinfo.setCreatedby(devuser.getId());
        appinfo.setCreationdate(new Date());
        appinfo.setLogopicpath("/statics/uploadfiles/"+a_logoPicPath.getOriginalFilename()); // 相对路径
        appinfo.setLogolocpath(server_path+a_logoPicPath.getOriginalFilename());
        appInfoService.save(appinfo);
        return "redirect:/dev/app/list";
    }

    /**
     * APP 修改
     * @param session
     * @param appinfo
     * @param attach
     * @return
     */
    @PostMapping("appinfomodify")
    public String appinfomodify(HttpSession session, AppInfo appinfo, MultipartFile attach){
        if(!attach.isEmpty()){ // null = true notnull = false
            // 1.实现文件上传
            String server_path = session.getServletContext().getRealPath("/statics/uploadfiles/");
            // 验证大小和图片规格 [未写]
            try {
                attach.transferTo(new File(server_path,attach.getOriginalFilename()));
                appinfo.setLogopicpath("/statics/uploadfiles/"+attach.getOriginalFilename()); // 相对路径
                appinfo.setLogolocpath(server_path+attach.getOriginalFilename());
            } catch (IOException e) {
            }
        }
        // 2.app修改
        DevUser devuser = (DevUser) session.getAttribute("devuser");
        appinfo.setUpdatedate(new Date());
        appinfo.setDevid(devuser.getId());
        appinfo.setModifyby(devuser.getId());
        appinfo.setModifydate(new Date());
        appInfoService.update(appinfo);
        return "redirect:/dev/app/list";
    }

    @RequestMapping("list")
    public String list(HttpSession session, Model model, @RequestParam(defaultValue = "1") Integer pageIndex, String querySoftwareName, Long queryStatus, Long queryFlatformId, Long queryCategoryLevel1, Long queryCategoryLevel2, Long queryCategoryLevel3){
        DevUser devuser = (DevUser) session.getAttribute("devuser");
        // 防止 session丢失
        model.addAttribute("appInfoList",appInfoService.queryAppInfo(pageIndex,devuser.getId(),querySoftwareName,queryStatus,queryFlatformId,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3));
        // 绑定 APP状态
        model.addAttribute("statusList",dataDictionaryService.queryDataByType("APP_STATUS"));
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
        model.addAttribute("queryStatus",queryStatus);
        model.addAttribute("queryFlatformId",queryFlatformId);
        model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);
        model.addAttribute("queryCategoryLevel2",queryCategoryLevel2);
        model.addAttribute("queryCategoryLevel3",queryCategoryLevel3);
        return "developer/appinfolist";
    }
}
