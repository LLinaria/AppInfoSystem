package com.jbit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

@Data
@Table(name = "app_version")
public class AppVersion implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Column(name = "appId")
    private Long appid;

    @Column(name = "versionNo")
    private String versionno;

    @Column(name = "versionInfo")
    private String versioninfo;

    @Column(name = "publishStatus")
    private Long publishstatus;

    @Column(name = "downloadLink")
    private String downloadlink;

    @Column(name = "versionSize")
    private BigDecimal versionsize;

    @Column(name = "createdBy")
    private Long createdby;

    @Column(name = "creationDate")
    private Date creationdate;

    @Column(name = "modifyBy")
    private Long modifyby;

    @Column(name = "modifyDate")
    private Date modifydate;

    @Column(name = "apkLocPath")
    private String apklocpath;

    @Column(name = "apkFileName")
    private String apkfilename;

    @Transient // 忽略映射 软件名称
    private  String appname;
    @Transient // 忽略映射 状态名称
    private  String publishstatusname;

    private static final long serialVersionUID = 1L;
}