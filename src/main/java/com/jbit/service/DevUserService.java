package com.jbit.service;

import com.jbit.mapper.DevUserMapper;
import com.jbit.pojo.DevUser;
import com.jbit.utils.AppUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class DevUserService {

    @Resource
    private DevUserMapper devUserMapper;

    /**
     * 用户登录
     * @param devcode
     * @param devpassword
     * @return
     */
    public DevUser queryLogin(String devcode, String devpassword){
        DevUser devUser = new DevUser();
        devUser.setDevcode(devcode);
        // 将密码明文 => 密文
        devUser.setDevpassword(AppUtils.encoderByMd5(devpassword));
        return devUserMapper.selectOne(devUser);
    }
}
