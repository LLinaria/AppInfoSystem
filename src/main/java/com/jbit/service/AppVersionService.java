package com.jbit.service;

import com.jbit.mapper.AppVersionMapper;
import com.jbit.pojo.AppVersion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;

    /**
     * 根据 ID 查询
     * @param id
     * @return
     */
    public AppVersion queryById(Long id){
        return appVersionMapper.selectByPrimaryKey(id);
    }
}
