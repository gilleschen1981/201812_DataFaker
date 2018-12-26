package com.microfocus.ucmdb.server.fakedata.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CIHolder implements Serializable{
    private String className;
    private Integer cloneNumber = 1;
    private Integer offset = 0;
    private Integer step = 1;
    // 用来作为topo当中的唯一标识符，不管name是v还是s，都用name的根形式。
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    // <attribute name, attribute value>
    // 需要用数字递增的属性
    private Map<String, String> attributes_v;
    // 值不变的属性
    private Map<String, String> attributes_s;


    public Map<String, String> getAttributes_s() {
        if(attributes_s == null){
            attributes_s = new HashMap<String, String>();
        }
        return attributes_s;
    }

    public void setAttributes_v(Map<String, String> attributes_v) {
        this.attributes_v = attributes_v;
    }

    public void setAttributes_s(Map<String, String> attributes_s) {
        this.attributes_s = attributes_s;
    }


    public CIHolder() {
    }

    public CIHolder(String className, String name) {
        this.className = className;
        this.name = name;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getCloneNumber() {
        return cloneNumber;
    }

    public void setCloneNumber(Integer cloneNumber) {
        this.cloneNumber = cloneNumber;
    }

    public Map<String, String> getAttributes_v() {
        if(attributes_v == null){
            attributes_v = new HashMap<String, String>();
        }
        return attributes_v;
    }

    public void addAttribute_v(String key, String value) {
        getAttributes_v().put(key, value);
    }

    public void addAttribute_s(String key, String value) {
        getAttributes_s().put(key, value);
    }

}
