package com.microfocus.ucmdb.server.fakedata.topo;

import com.hp.ucmdb.api.*;
import com.hp.ucmdb.api.classmodel.ClassModelService;
import com.hp.ucmdb.api.topology.*;
import com.hp.ucmdb.api.types.CI;
import com.hp.ucmdb.api.types.UcmdbId;
import com.microfocus.ucmdb.server.classmodel.ClassModelLoader;
import com.microfocus.ucmdb.server.fakedata.cache.CICacheItem;
import com.microfocus.ucmdb.server.fakedata.cache.CreatedCICache;
import com.microfocus.ucmdb.server.fakedata.model.*;
import org.apache.log4j.Logger;
import com.microfocus.ucmdb.server.fakedata.util.IncrementUtil;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopoGenerator {
    final Logger logger = Logger.getLogger(TopoGenerator.class);
    final private static String RELATION_COMPOSITION = "composition";
    private static TopoGenerator _instance = new TopoGenerator();

    public static TopoGenerator getInstance(){
        return _instance;
    }

    private TopoGenerator() {
    }

    public void insertTopologyFromTopoHolder(TopologyUpdateService service, TopoHolder topo){
        if(!TopoValidator.isTopoValid(topo)){
            logger.error("[ERROR]Topo invalid!!!");
            return;
        }
        TopologyUpdateFactory factory = service.getFactory();
        for(int topoCount = 1; topoCount <= topo.getClone(); topoCount++){
            TopologyModificationData data = service.getFactory().createTopologyModificationData();
            List<CI> generatedCIs = generateTopo(topo.getCiMap(), topo.getRelationList(), topoCount, data);
            service.createGracefully(data, CreateGracefullyMode.UPDATE_EXISTING);

            logger.info("[CI generated]");
            for(CI resultCI : generatedCIs){
                logger.info("{Type: " + resultCI.getType() + "}, {ID: " + resultCI.getId() + "}");
                CreatedCICache.getInstance().addCacheItem(resultCI.getType(), resultCI.getId().getAsString());
            }
            CreatedCICache.getInstance().saveCache();


        }

    }

    private List<CI> generateTopo(Map<String, CIHolder> ciMap, List<RelationHolder> relationList, int topoCount, TopologyModificationData data) {
        List<CI> rlt = new ArrayList<CI>();
        Map<String, List<CI>> generatedCIMap = new HashMap<String, List<CI>>();
        for(Map.Entry<String, CIHolder> entry : ciMap.entrySet()){
            String name = entry.getKey();
            CIHolder ci = entry.getValue();
            for(int ciCount = 1; ciCount <= ci.getCloneNumber(); ciCount++){
                CI newCI = data.addCI(ci.getClassName());
                if(ci.getAttributes_v() != null){
                    for(Map.Entry<String, String> attrEntry :ci.getAttributes_v().entrySet()) {
                        int count = (topoCount - 1) * ci.getCloneNumber() + ciCount;
                        newCI.setStringProperty(attrEntry.getKey(), IncrementUtil.generateIncrementalValue(attrEntry.getValue(), count));
                    }
                 }
                if(ci.getAttributes_s() != null){
                    for(Map.Entry<String,String> attrEntry : ci.getAttributes_s().entrySet()){
                        newCI.setStringProperty(attrEntry.getKey(), attrEntry.getValue());
                    }
                }
                List<CI> ciList = generatedCIMap.getOrDefault(name, new ArrayList<CI>());
                ciList.add(newCI);
                generatedCIMap.put(name, ciList);
                rlt.add(newCI);
            }
        }

        for(RelationHolder relationHolder : relationList){
            List<CI> source = generatedCIMap.get(relationHolder.getEnd1Id());
            List<CI> target = generatedCIMap.get(relationHolder.getEnd2Id());
            if(source == null || source.size() <= 0 || target == null || target.size() <= 0){
                logger.error("[Relation error]End ci missing: " + relationHolder.getEnd1Id() + " ---> " + relationHolder.getEnd2Id());
                continue;
            }

            CIHolder end1CI = ciMap.get(relationHolder.getEnd1Id());
            CIHolder end2CI = ciMap.get(relationHolder.getEnd2Id());
            if(end1CI == null || end2CI == null){
                logger.error("[Relation error]End ciholder missing: " + relationHolder.getEnd1Id() + " ---> " + relationHolder.getEnd2Id());
                continue;
            }

            float scale = end2CI.getCloneNumber()/end1CI.getCloneNumber();
            if (scale <= 0){
                logger.error("[Relation error]relation scale don't match: " + relationHolder.getEnd1Id() + " ---> " + relationHolder.getEnd2Id());
                continue;
            }

            for(int i = 0; i < source.size(); i++){
                for(int s = 0; s < scale; s++){
                    int count = (int) Math.floor(i*scale);
                    data.addRelation(relationHolder.getRelationType(), source.get(i), target.get(count + s));
                }
            }

        }

        return rlt;
    }

    public void clearAll(TopologyUpdateService service){
        TopologyModificationData data = service.getFactory().createTopologyModificationData();
        for(CICacheItem item : CreatedCICache.getInstance().getCachedItems().values()){
            data.addCI(service.getFactory().restoreCIIdFromString(item.getGlobalId()), item.getClassType());
        }
        service.deleteGracefully(data);
        CreatedCICache.getInstance().clearCache();
    }


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
        TopologyUpdateService topologyUpdateService = service.getTopologyUpdateService();

        TopoHolder topoHolder = CIJsonInterpreter.loadJson(new File("1.txt"));
        TopoGenerator.getInstance().insertTopologyFromTopoHolder(topologyUpdateService, topoHolder);


    }
}
