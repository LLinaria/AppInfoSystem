package com.jbit.service;

import com.jbit.mapper.BackendUserMapper;
import com.jbit.pojo.BackendUser;
import com.jbit.utils.AppUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BackendUserService {

    @Resource
    private BackendUserMapper backendUserMapper;

    @Resource
    private DataDictionaryService dataDictionaryService;

    /**
     * 完成后台登录
     * @param usercode
     * @param userpassword
     * @return
     */
    public BackendUser queryLogin(String usercode, String userpassword){
        BackendUser backendUser = new BackendUser();
        backendUser.setUsercode(usercode);
        backendUser.setUserpassword(AppUtils.encoderByMd5(userpassword));
        BackendUser backUser = backendUserMapper.selectOne(backendUser);
        // 处理角色名称
        backUser.setUsertypename(dataDictionaryService.queryData("USER_TYPE",backUser.getUsertype()).getValuename());
        return backUser;
    }
}
