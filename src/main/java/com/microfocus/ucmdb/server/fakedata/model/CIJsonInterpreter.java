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
        CIHolder ci1 = new CIHolder("node", "webapp");
        ci1.addAttribute_v("name","webapp");
        topo.addCI(ci1.getName(), ci1);

        CIHolder ci2 = new CIHolder("node", "dbnode");
        ci2.addAttribute_v("name","dbnode");
        topo.addCI(ci2.getName(), ci2);

        CIHolder ci3 = new CIHolder("process", "webprocess");
        ci3.addAttribute_v("name","tomcat.exe");
        ci3.addAttribute_v("process_cmdline","tomcat/cmdline");
        ci3.addAttribute_v("process_user","tomcatuser");
        topo.addCI(ci3.getName(),ci3);


        CIHolder ci5 = new CIHolder("ip_service_endpoint", "ip_service_endpoint");
        ci5.addAttribute_s("ip_service_name", "db");
        ci5.addAttribute_v("bound_to_ip_address", "1.1.1.1");
        ci5.addAttribute_s("network_port_number", "1521");
        ci5.addAttribute_s("port_type", "tcp");
        topo.addCI(ci5.getName(), ci5);

        RelationHolder relation1 = new RelationHolder();
        relation1.setRelationType("composition");
        relation1.setEnd1Id("webapp");
        relation1.setEnd2Id("webprocess");
        topo.addRelation(relation1);

        RelationHolder relation2 = new RelationHolder();
        relation2.setRelationType("composition");
        relation2.setEnd1Id("dbnode");
        relation2.setEnd2Id("ip_service_endpoint");
        topo.addRelation(relation2);


        RelationHolder relation3 = new RelationHolder();
        relation3.setRelationType("client_server");
        relation3.setEnd1Id("webprocess");
        relation3.setEnd2Id("ip_service_endpoint");
        relation3.addAttribute_s("clientserver_protocol", "tcp");
        topo.addRelation(relation3);


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
