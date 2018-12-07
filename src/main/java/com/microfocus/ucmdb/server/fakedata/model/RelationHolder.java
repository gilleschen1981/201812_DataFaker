package com.microfocus.ucmdb.server.fakedata.model;

import java.io.Serializable;

public class RelationHolder implements Serializable {
    private String relationType;
    // 这里的id是name的根形式
    private String end1Id;
    private String end2Id;

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
