package com.jbit.service;

import com.jbit.mapper.AppVersionMapper;
import com.jbit.pojo.AppVersion;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;

    @Resource
    private AppInfoService appInfoService;

    @Resource
    private DataDictionaryService dataDictionaryService;

    /**
     * 根据 ID 查询
     *
     * @param id
     * @return
     */
    public AppVersion queryById(Long id) {
        return appVersionMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据 appid 查询信息绑定list
     * @param appid
     * @return
     */
    public List<AppVersion> queryByAppId(Long appid) {
        AppVersion appVersion = new AppVersion();
        appVersion.setAppid(appid);
        List<AppVersion> appVersions = appVersionMapper.select(appVersion);
        bindData(appVersions);
        return appVersions;
    }

    private void bindData(List<AppVersion> appVersions) {
        appVersions.forEach((app) -> {
            // 软件名称
            app.setAppname(appInfoService.querySoftwareNameById(app.getAppid()).getSoftwarename());
            // 状态
            app.setPublishstatusname(dataDictionaryService.queryData("PUBLISH_STATUS", app.getPublishstatus()).getValuename());
        });
    }

    public void save(AppVersion appVersion) {
        appVersionMapper.insertSelective(appVersion);
    }

    public void deleteByAppId(Long id) {
        // 处理条件
        Example example=new Example(AppVersion.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("appid",id);
        appVersionMapper.deleteByExample(example);
    }

    public void update(AppVersion appVersion) {
        appVersionMapper.updateByPrimaryKeySelective(appVersion);
    }
}
