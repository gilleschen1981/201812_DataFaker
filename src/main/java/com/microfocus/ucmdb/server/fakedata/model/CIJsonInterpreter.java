package com.microfocus.ucmdb.server.fakedata.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class CIJsonInterpreter {
    public static TopoHolder loadJson(File file){
        TopoHolder rlt = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            rlt = mapper.readValue(file, TopoHolder.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rlt;
    }

    public static void writeJson(TopoHolder topo, File file){
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        try {
            mapper.writeValue(file, topo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        TopoHolder topo = new TopoHolder();
        CIHolder ci1 = new CIHolder("node", "node");
        ci1.addAttribute_v("name","node");
        topo.addCI(ci1.getName(), ci1);

        CIHolder ci2 = new CIHolder("running_software", "running_software");
        ci2.addAttribute_v("name", "oracle");
        ci2.addAttribute_s("product_name", "oracle_database");
        topo.addCI(ci2.getName(), ci2);

        CIHolder ci3 = new CIHolder("process", "process");
        ci3.addAttribute_v("name","oracle");
        ci3.addAttribute_v("process_cmdline","oracle/cmdline");
        ci3.addAttribute_v("process_user","oracleuser");
        topo.addCI(ci3.getName(),ci3);


        CIHolder ci4 = new CIHolder("ip_service_endpoint", "ip_service_endpoint");
        ci4.addAttribute_s("ip_service_name", "db");
        ci4.addAttribute_v("bound_to_ip_address", "1.1.1.1");
        ci4.addAttribute_s("network_port_number", "8080");
        ci4.addAttribute_s("port_type", "tcp");
        topo.addCI(ci4.getName(), ci4);

        CIHolder ci5 = new CIHolder("ip_address", "ip_address");
        ci5.addAttribute_v("name", "1.1.1.1");
        topo.addCI(ci5.getName(), ci5);

        RelationHolder relation1 = new RelationHolder();
        relation1.setRelationType("composition");
        relation1.setEnd1Id("node");
        relation1.setEnd2Id("running_software");
        topo.addRelation(relation1);

        RelationHolder relation2 = new RelationHolder();
        relation2.setRelationType("composition");
        relation2.setEnd1Id("node");
        relation2.setEnd2Id("process");
        topo.addRelation(relation2);

        RelationHolder relation3 = new RelationHolder();
        relation3.setRelationType("usage");
        relation3.setEnd1Id("process");
        relation3.setEnd2Id("ip_service_endpoint");
        topo.addRelation(relation3);

        RelationHolder relation4 = new RelationHolder();
        relation4.setRelationType("containment");
        relation4.setEnd1Id("node");
        relation4.setEnd2Id("ip_address");
        topo.addRelation(relation4);

        RelationHolder relation5 = new RelationHolder();
        relation5.setRelationType("composition");
        relation5.setEnd1Id("node");
        relation5.setEnd2Id("ip_service_endpoint");
        topo.addRelation(relation5);

        if(!TopoValidator.isTopoValid(topo))
        {
            System.out.println("Not valid.");
        }
        File file = new File("1.txt");
        if(file.exists()){
            file.delete();
        }
        writeJson(topo, file);

    }
}
