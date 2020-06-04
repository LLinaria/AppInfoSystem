package com.jbit.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jbit.mapper.AppInfoMapper;
import com.jbit.pojo.AppInfo;
import com.jbit.pojo.AppVersion;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppInfoService {
    @Resource
    private AppInfoMapper appInfoMapper;

    @Resource
    private DataDictionaryService dataDictionaryService;

    @Resource
    private AppCategoryService appCategoryService;

    @Resource
    private AppVersionService appVersionService;

    /**
     * 验证 APKName是否存在
     * @param apkname
     * @return
     */
    public AppInfo queryApkexist(String apkname){
        AppInfo appInfo = new AppInfo();
        appInfo.setApkname(apkname);
        return appInfoMapper.selectOne(appInfo);
    }

    /**
     * App列表查询 每一个 Dev 登录后只查看属于自己的 AppInfo
     * @param pageIndex
     * @param devId
     * @param querySoftwareName
     * @param queryStatus
     * @param queryFlatformId
     * @param queryCategoryLevel1
     * @param queryCategoryLevel2
     * @param queryCategoryLevel3
     * @return
     */
    public PageInfo<AppInfo> queryAppInfo(Integer pageIndex, Long devId, String querySoftwareName, Long queryStatus, Long queryFlatformId, Long queryCategoryLevel1, Long queryCategoryLevel2, Long queryCategoryLevel3){
        // 实现分页
        PageHelper.startPage(pageIndex,5);
        // 处理查询条件
        Example example=new Example(AppInfo.class);
        Example.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotEmpty(querySoftwareName)){
            criteria.andLike("softwarename","%"+querySoftwareName+"%");
        }
        if(queryStatus != null && queryStatus != 0){
            criteria.andEqualTo("status",queryStatus);
        }
        if(queryFlatformId != null && queryFlatformId != 0){
            criteria.andEqualTo("flatformid",queryFlatformId);
        }
        if(queryCategoryLevel1 != null && queryCategoryLevel1 != 0){
            criteria.andEqualTo("categorylevel1",queryCategoryLevel1);
        }
        if(queryCategoryLevel2 != null && queryCategoryLevel2 != 0){
            criteria.andEqualTo("categorylevel2",queryCategoryLevel2);
        }
        if(queryCategoryLevel3 != null && queryCategoryLevel3 != 0){
            criteria.andEqualTo("categorylevel3",queryCategoryLevel3);
        }
        if(devId!=null){
            criteria.andEqualTo("devid",devId);
        }
        example.orderBy("creationdate").desc();
        List<AppInfo> appInfos = appInfoMapper.selectByExample(example);
        // 绑定其他数据
        bindData(appInfos);
        // 返回 处理分页
        return new PageInfo<>(appInfos);
    }

    /**
     * 绑定数据
     * @param appInfos
     */
    private void bindData(List<AppInfo> appInfos) {
        appInfos.forEach((app)->{
            // 所属平台
            app.setFlatformname(dataDictionaryService.queryData("APP_FLATFORM",app.getFlatformid()).getValuename());
            // 加载分类
            app.setCategorylevel1name(appCategoryService.queryById(app.getCategorylevel1()).getCategoryname());
            app.setCategorylevel2name(appCategoryService.queryById(app.getCategorylevel2()).getCategoryname());
            app.setCategorylevel3name(appCategoryService.queryById(app.getCategorylevel3()).getCategoryname());
            // 状态
            app.setStatusname(dataDictionaryService.queryData("APP_STATUS",app.getStatus()).getValuename());
            // 版本号
            AppVersion appVersion = appVersionService.queryById(app.getVersionid());
            if(appVersion!=null){
                app.setVersionno(appVersion.getVersionno());
            }
        });
    }

    @Transactional
    public void save(AppInfo appinfo) {
        appInfoMapper.insertSelective(appinfo);
    }

    public AppInfo queryById(Long id) {
        AppInfo appInfo = appInfoMapper.selectByPrimaryKey(id);
        // 处理状态名称
        appInfo.setStatusname(dataDictionaryService.queryData("APP_STATUS",appInfo.getStatus()).getValuename());
        return appInfo;
    }

    public AppInfo queryByID(Long id) {
        AppInfo appInfo = appInfoMapper.selectByPrimaryKey(id);
        // 所属平台
        appInfo.setFlatformname(dataDictionaryService.queryData("APP_FLATFORM",appInfo.getFlatformid()).getValuename());
        // 加载分类
        appInfo.setCategorylevel1name(appCategoryService.queryById(appInfo.getCategorylevel1()).getCategoryname());
        appInfo.setCategorylevel2name(appCategoryService.queryById(appInfo.getCategorylevel2()).getCategoryname());
        appInfo.setCategorylevel3name(appCategoryService.queryById(appInfo.getCategorylevel3()).getCategoryname());
        // 处理状态名称
        appInfo.setStatusname(dataDictionaryService.queryData("APP_STATUS",appInfo.getStatus()).getValuename());
        return appInfo;
    }

    public void update(AppInfo appInfo) {
        appInfoMapper.updateByPrimaryKeySelective(appInfo);
    }

    public int delete(Long id) {
        return appInfoMapper.deleteByPrimaryKey(id);
    }

    public AppInfo querySoftwareNameById(Long appid) {
        return appInfoMapper.selectByPrimaryKey(appid);
    }
}
