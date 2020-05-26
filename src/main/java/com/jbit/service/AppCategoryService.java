package com.jbit.service;

import com.jbit.mapper.AppCategoryMapper;
import com.jbit.pojo.AppCategory;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppCategoryService {
    @Resource
    private AppCategoryMapper appCategoryMapper;

    /**
     * 根据 ID查询
     * @param id
     * @return
     */
    public AppCategory queryById(Long id){
        return appCategoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询一级分类
     * @param parentid
     * @return
     */
    public List<AppCategory> queryByParentId(Long parentid){
        //1.处理查询条件
        Example example=new Example(AppCategory.class);
        Example.Criteria criteria=example.createCriteria();
        //2.处理分类
        if(parentid == null){
            criteria.andIsNull("parentid");
        }else {
            criteria.andEqualTo("parentid",parentid);
        }
        //3.返回查询数据
        return appCategoryMapper.selectByExample(example);
    }
}
