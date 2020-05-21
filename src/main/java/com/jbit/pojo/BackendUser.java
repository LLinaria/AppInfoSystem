package com.jbit.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

@Data
@Table(name = "backend_user")
public class BackendUser implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Column(name = "userCode")
    private String usercode;

    @Column(name = "userName")
    private String username;

    @Column(name = "userType")
    private Long usertype;

    @Column(name = "createdBy")
    private Long createdby;

    @Column(name = "creationDate")
    private Date creationdate;

    @Column(name = "modifyBy")
    private Long modifyby;

    @Column(name = "modifyDate")
    private Date modifydate;

    @Column(name = "userPassword")
    private String userpassword;

    private static final long serialVersionUID = 1L;
}