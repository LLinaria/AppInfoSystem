package com.jbit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

@Data
@Table(name = "app_info")
public class AppInfo implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Column(name = "softwareName")
    private String softwarename;

    @Column(name = "APKName")
    private String apkname;

    @Column(name = "supportROM")
    private String supportrom;

    @Column(name = "interfaceLanguage")
    private String interfacelanguage;

    @Column(name = "softwareSize")
    private BigDecimal softwaresize;

    @Column(name = "updateDate")
    private Date updatedate;

    @Column(name = "devId")
    private Long devid;

    @Column(name = "appInfo")
    private String appinfo;

    @Column(name = "status")
    private Long status;

    @Column(name = "onSaleDate")
    private Date onsaledate;

    @Column(name = "offSaleDate")
    private Date offsaledate;

    @Column(name = "flatformId")
    private Long flatformid;

    @Column(name = "categoryLevel3")
    private Long categorylevel3;

    @Column(name = "downloads")
    private Long downloads;

    @Column(name = "createdBy")
    private Long createdby;

    @Column(name = "creationDate")
    private Date creationdate;

    @Column(name = "modifyBy")
    private Long modifyby;

    @Column(name = "modifyDate")
    private Date modifydate;

    @Column(name = "categoryLevel1")
    private Long categorylevel1;

    @Column(name = "categoryLevel2")
    private Long categorylevel2;

    @Column(name = "logoPicPath")
    private String logopicpath;

    @Column(name = "logoLocPath")
    private String logolocpath;

    @Column(name = "versionId")
    private Long versionid;

    private static final long serialVersionUID = 1L;
}