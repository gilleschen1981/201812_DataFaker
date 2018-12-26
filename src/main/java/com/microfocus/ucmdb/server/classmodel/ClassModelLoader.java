package com.microfocus.ucmdb.server.classmodel;

import com.hp.ucmdb.api.*;
import com.hp.ucmdb.api.classmodel.ClassDefinition;
import com.hp.ucmdb.api.classmodel.ClassModelService;
import com.hp.ucmdb.api.classmodel.ValidRelation;
import com.hp.ucmdb.api.reconciliation.ReconciliationService;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ClassModelLoader {
    public static void main(String[] args) {
        String hostname = "16.187.189.78";
        int port = 8443;
        String username = "admin";
        String password = "Admin_1234";
        UcmdbServiceProvider serviceProvider = null;
        try {
            serviceProvider = UcmdbServiceFactory.getServiceProvider("https", hostname, port);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Creating a client context according to the name of this integration (for auditing)
        ClientContext clientContext = serviceProvider.createClientContext("TestTopo");
        //Creating the credentials for authentication
        Credentials credentials = serviceProvider.createCredentials(username, password);
        // Creating the connection object
        UcmdbService service = serviceProvider.connect(credentials,clientContext);

        ClassModelService clm = service.getClassModelService();

        loadClassModel(clm);
        ClassModelRepository repo = ClassModelRepository.getInstance();
        List<String> classNameList = repo.getAllClassName();
        Collections.sort(classNameList);
        System.out.println(classNameList.size());
        for(String s : classNameList){
            ClassDefinition clt = repo.getClassMap().get(s);
            String output = clt.getDisplayName() + " : " + clt.getName();
            System.out.println(output);
        }

        System.out.println(repo.getValidLinks().size());

    }

    public static void loadClassModel(ClassModelService cls){
        ClassModelRepository cmRepo = ClassModelRepository.getInstance();
        Collection<ClassDefinition> collection = cls.getAllClasses();
        Iterator<ClassDefinition> it = collection.iterator();
        while(it.hasNext()){
            ClassDefinition cd = it.next();
            cmRepo.addClassDefinition(cd);
        }

        Collection<ValidRelation> validRelationsCollection = cls.getValidRelations();
        Iterator<ValidRelation> validRelationIterator = validRelationsCollection.iterator();
        while(validRelationIterator.hasNext()){
            ValidRelation validRelation = validRelationIterator.next();
            cmRepo.addValidLinks(validRelation);
        }
    }
}
