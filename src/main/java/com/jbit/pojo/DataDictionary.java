package com.jbit.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

@Data
@Table(name = "data_dictionary")
public class DataDictionary implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Column(name = "typeCode")
    private String typecode;

    @Column(name = "typeName")
    private String typename;

    @Column(name = "valueId")
    private Long valueid;

    @Column(name = "valueName")
    private String valuename;

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