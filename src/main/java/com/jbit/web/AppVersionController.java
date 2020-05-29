package com.jbit.web;

import com.jbit.entity.JsonResult;
import com.jbit.pojo.AppInfo;
import com.jbit.pojo.AppVersion;
import com.jbit.pojo.DevUser;
import com.jbit.service.AppInfoService;
import com.jbit.service.AppVersionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class AppVersionController {

    @Resource
    private AppVersionService appVersionService;

    @Resource
    private AppInfoService appInfoService;

    @GetMapping("delfile")
    @ResponseBody
    public JsonResult delfile(Long id, String flag){
        if (flag.equals("apk")){ // 删除 apk
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
        return "redirect:/appversionadd/"+appVersion.getAppid();
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
}
