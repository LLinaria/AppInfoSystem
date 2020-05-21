package com.jbit.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

@Data
@Table(name = "dev_user")
public class DevUser implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Column(name = "devCode")
    private String devcode;

    @Column(name = "devName")
    private String devname;

    @Column(name = "devPassword")
    private String devpassword;

    @Column(name = "devEmail")
    private String devemail;

    @Column(name = "devInfo")
    private String devinfo;

    @Column(name = "createdBy")
    private Long createdby;

    @Column(name = "creationDate")
    private Date creationdate;

    @Column(name = "modifyBy")
    private Long modifyby;

    @Column(name = "modifyDate")
    private Date modifydate;

    private static final long serialVersionUID = 1L;
}