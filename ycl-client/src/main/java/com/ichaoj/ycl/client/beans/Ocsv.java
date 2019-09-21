package com.ichaoj.ycl.client.beans;

import lombok.Data;

import java.util.List;

@Data
public class Ocsv {
    /**
     * 对象名称
     */
    private String name;
    /**
     * 对象内容（type为文件时，该值为文件的访问路径（http）；type为键值对数据时）
     */
    private String value;
    /**
     * 文件的名称
     */
    private String fileName;
    /**
     * 对象类型（对象是键值对数据类型还是文件类型）
     */
    private String type;
    /**
     * 子元素信息
     */
    private List<Ocsv> subOcsv;
    /**
     * 子元素信息String
     */
    private String subOcsvStr;

    public Ocsv() { }

    public Ocsv(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Ocsv(String name, String value, String type, String fileName) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.fileName = fileName;
    }

    public Ocsv(String name, String value, String type, String fileName, List<Ocsv> subOcsv) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.fileName = fileName;
        this.subOcsv = subOcsv;
    }
}
