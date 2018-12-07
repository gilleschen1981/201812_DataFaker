package com.microfocus.ucmdb.server.classmodel;


import com.hp.ucmdb.api.classmodel.ClassDefinition;
import com.hp.ucmdb.api.classmodel.ValidRelation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassModelRepository {
    private static ClassModelRepository _instance;
    public static ClassModelRepository getInstance(){
        if(_instance == null){
            _instance = new ClassModelRepository();
        }
        return _instance;
    }

    private ClassModelRepository() {
    }

    Map<String, ClassDefinition> classMap = new HashMap<String, ClassDefinition>(2000);
    Map<String, List<ValidRelation>> validLinks = new HashMap<String, List<ValidRelation>>();

    public Map<String, List<ValidRelation>> getValidLinks() {
        if(validLinks == null){
            validLinks = new HashMap<String, List<ValidRelation>>();
        }
        return validLinks;
    }

    public void addValidLinks(ValidRelation validLink) {
        List<ValidRelation> list = getValidLinks().getOrDefault(validLink.getEnd1TypeName(), new ArrayList<ValidRelation>());
        list.add(validLink);
        getValidLinks().put(validLink.getEnd1TypeName(), list);
    }

    public Map<String, ClassDefinition> getClassMap() {
        if(classMap == null){
            classMap = new HashMap<String, ClassDefinition>(2000);
        }
        return classMap;
    }

    public void addClassDefinition(ClassDefinition cd){
        getClassMap().put(cd.getName(), cd);
    }

    public List<String> getAllClassName() {
        List<String> rlt = new ArrayList<String>(getClassMap().size());
        for( String s : getClassMap().keySet()){
            rlt.add(s);
        }
        return rlt;
    }
}
