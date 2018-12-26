package com.microfocus.ucmdb.server.fakedata.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RelationHolder implements Serializable {
    private String relationType;
    // 这里的id是name的根形式
    private String end1Id;
    private String end2Id;

    // 值不变的属性
    private Map<String, String> attributes_s;


    public Map<String, String> getAttributes_s() {
        if(attributes_s == null){
            attributes_s = new HashMap<String, String>();
        }
        return attributes_s;
    }

    public void setAttributes_s(Map<String, String> attributes_s) {
        this.attributes_s = attributes_s;
    }

    public void addAttribute_s(String key, String value) {
        getAttributes_s().put(key, value);
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getEnd1Id() {
        return end1Id;
    }

    public void setEnd1Id(String end1Id) {
        this.end1Id = end1Id;
    }

    public String getEnd2Id() {
        return end2Id;
    }

    public void setEnd2Id(String end2Id) {
        this.end2Id = end2Id;
    }
}
