package com.jbit.service;

import com.jbit.mapper.DataDictionaryMapper;
import com.jbit.pojo.DataDictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataDictionaryService {
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    /**
     * 查询数据
     * @param typecode 类型
     * @param valueid 值
     * @return
     */
    public DataDictionary queryData(String typecode, Long valueid){
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setTypecode(typecode);
        dataDictionary.setValueid(valueid);
        return dataDictionaryMapper.selectOne(dataDictionary);
    }

    /**
     * 查询状态和所属平台
     * @param typecode
     * @return
     */
    public List<DataDictionary> queryDataByType(String typecode){
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setTypecode(typecode);
        return dataDictionaryMapper.select(dataDictionary);
    }
}
