package com.microfocus.ucmdb.server.fakedata.model;

import com.hp.ucmdb.api.types.Relation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopoHolder implements Serializable {
    private Integer clone = 1;

    // <id, CIholder>  id是name的根形式
    private Map<String, CIHolder> ciMap;

    private List<RelationHolder> relationList;

    public Integer getClone() {
        return clone;
    }

    public void setClone(Integer clone) {
        this.clone = clone;
    }

    public Map<String, CIHolder> getCiMap() {
        if(ciMap == null){
            ciMap = new HashMap<String,CIHolder>();
        }
        return ciMap;
    }

    public void setCiMap(Map<String, CIHolder> ciMap) {
        this.ciMap = ciMap;
    }
    public void addCI(String name, CIHolder ci){
        getCiMap().put(name, ci);
    }

    public List<RelationHolder> getRelationList() {
        if(relationList == null){
            relationList = new ArrayList<RelationHolder>();
        }
        return relationList;
    }
    public void addRelation(RelationHolder relation){
        getRelationList().add(relation);
    }

    public void setRelationList(List<RelationHolder> relationList) {
        this.relationList = relationList;
    }
}
