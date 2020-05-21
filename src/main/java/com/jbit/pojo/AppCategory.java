package com.jbit.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

@Data
@Table(name = "app_category")
public class AppCategory implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Column(name = "categoryCode")
    private String categorycode;

    @Column(name = "categoryName")
    private String categoryname;

    @Column(name = "parentId")
    private Long parentid;

    @Column(name = "createdBy")
    private Long createdby;

    @Column(name = "creationTime")
    private Date creationtime;

    @Column(name = "modifyBy")
    private Long modifyby;

    @Column(name = "modifyDate")
    private Date modifydate;

    private static final long serialVersionUID = 1L;
}