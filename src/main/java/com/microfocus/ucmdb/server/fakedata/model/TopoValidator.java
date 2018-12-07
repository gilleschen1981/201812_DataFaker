package com.microfocus.ucmdb.server.fakedata.model;

import org.apache.log4j.Logger;

public class TopoValidator {
    final static Logger logger = Logger.getLogger(TopoValidator.class);
    public static boolean isTopoValid(TopoHolder topo){
        // Check all CI type exist

        // Check all attributes exist

        // Check all relationship's 2 ends id exist in CIMap
        // check end2 ci clone number should bigger than end1
        for(RelationHolder rh : topo.getRelationList()){
            CIHolder end1 = topo.getCiMap().get(rh.getEnd1Id());
            if(end1 == null){
                logger.error("[VALIDATION]End1 missing: " + rh.getEnd1Id());
                return false;
            }
            CIHolder end2 = topo.getCiMap().get(rh.getEnd2Id());
            if(end2 == null){
                logger.error("[VALIDATION]End2 missing: " + rh.getEnd2Id());
                return false;
            }

            if(((end1.getCloneNumber() % end2.getCloneNumber()) != 0) && ((end2.getCloneNumber() % end1.getCloneNumber()) != 0)){
                logger.error("[VALIDATION]clone number don't match: " + rh.getEnd1Id() + "--->" + rh.getEnd2Id());
                return false;
            }
        }

        return true;
    }
}
