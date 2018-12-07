package com.microfocus.ucmdb.server.fakedata;

import com.hp.ucmdb.api.*;
import com.hp.ucmdb.api.discovery.services.DDMConfigurationService;
import com.hp.ucmdb.api.topology.CreateMode;
import com.hp.ucmdb.api.topology.TopologyModificationData;
import com.hp.ucmdb.api.topology.TopologyUpdateFactory;
import com.hp.ucmdb.api.topology.TopologyUpdateService;
import com.hp.ucmdb.api.types.CI;
import com.hp.ucmdb.api.types.Relation;
import java.net.MalformedURLException;


public class DataFaker {
    static final String CIT_NAME_HOST ="node";
    static final String CIT_NAME_SNMP ="snmp";
    static final String CIT_NAME_DATABASE ="database";
    static final String CIT_NAME_INTERFACE ="interface";
    static final String ATTRIBUTE_NAME ="name";
    static final String ATTRIBUTE_PRODUCT_NAME ="product_name";
    static final String ATTRIBUTE_PROBE_NAME ="ip_probename";
    static final String CIT_NAME_IP = "ip_address";
    static final String ATTRIBUTE_NAME_IP_DOMAIN ="routing_domain";
    static final String ATTRIBUTE_NAME_IP_ADDRESS_SHORT ="name";
    static final String CIT_NAME_BA_NODE_LINK = "containment";
    static final String CIT_NAME_CLIENTSERVER = "client_server";
    static final String CIT_NAME_BUSINESSAPPLICATION = "business_application";
    static final String CIT_NAME_COMPOSITION = "composition";
    static final String ATTRIBUTE_INTERFACENAME ="interface_name";




    public static void main(String[] args) {
        // parameter analysis
        String hostname = args[0];
        int port = 8443;
        String username = args[1];
        String password = args[2];
//        String ipFile = args[3];

        // link to UCMDB
        //Creating a service provider for a given UCMDB server address
        UcmdbServiceProvider serviceProvider = null;
        try {
            serviceProvider = UcmdbServiceFactory.getServiceProvider("https", hostname, port);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Creating a client context according to the name of this integration (for auditing)
        ClientContext clientContext = serviceProvider.createClientContext("MyAppName");
        //Creating the credentials for authentication
        Credentials credentials = serviceProvider.createCredentials(username, password);
        // Creating the connection object
        UcmdbService service = serviceProvider.connect(credentials,clientContext);

//        createBusinessApplicationToNode(service, "ba1", "node1");
//        createNodeClientServerNode(service, "node2", 3306, "node3");
//        createNodeToDatabase(service, "ap-south-1", "DemoRDS-02");
//        createNodeToIp(service, "node", "1.1.1.1");
//        createNodeToSNMP(service, "node", "snmp");
        createBusinessApplication(service,"ba1");
    }


    private static void createNodeClientServerNode(UcmdbService service, String node1, int i, String node2) {
        final TopologyUpdateService topologyUpdateService = service.getTopologyUpdateService();
        final TopologyUpdateFactory factory = topologyUpdateService.getFactory();
        final TopologyModificationData addData = factory.createTopologyModificationData();

        // Create a CI of type node.
        final CI addedNode1 = addData.addCI(CIT_NAME_HOST);
        addedNode1.setStringProperty(ATTRIBUTE_NAME, node1);
        final CI addedNode2 = addData.addCI(CIT_NAME_HOST);
        addedNode2.setStringProperty(ATTRIBUTE_NAME, node2);

        final Relation addedRelation1 = addData.addRelation(CIT_NAME_CLIENTSERVER, addedNode1, addedNode2);
        addedRelation1.setPropertyValue("name", String.valueOf(i));
        addedRelation1.setPropertyValue("clientserver_protocol", "TCP");

        // Add the data to the UCMDB, in case that the object exists - it will be updated
        topologyUpdateService.create(addData, CreateMode.UPDATE_EXISTING);
        // We print the node ID from the UCMDB.
        System.out.println("The added node ID is : " + addedNode1.getId());

    }

    private static void createBusinessApplicationToNode(UcmdbService service, String baName, String nodeName) {

        final TopologyUpdateService topologyUpdateService = service.getTopologyUpdateService();
        final TopologyUpdateFactory factory = topologyUpdateService.getFactory();
        final TopologyModificationData addData = factory.createTopologyModificationData();

        // Create a CI of type node.
        final CI addedNode = addData.addCI(CIT_NAME_HOST);
        addedNode.setStringProperty(ATTRIBUTE_NAME, nodeName);
        final CI addedNode2 = addData.addCI(CIT_NAME_HOST);
        addedNode2.setStringProperty(ATTRIBUTE_NAME, nodeName+"_2");

        final CI addedBusinessApplication = addData.addCI(CIT_NAME_BUSINESSAPPLICATION);
        addedBusinessApplication.setStringProperty(ATTRIBUTE_NAME, baName);


        addData.addRelation(CIT_NAME_BA_NODE_LINK, addedBusinessApplication, addedNode);
        addData.addRelation(CIT_NAME_BA_NODE_LINK, addedBusinessApplication, addedNode2);
        // Add the data to the UCMDB, in case that the object exists - it will be updated
        topologyUpdateService.create(addData, CreateMode.UPDATE_EXISTING);
        // We print the node ID from the UCMDB.
        System.out.println("The added app ID is : " + addedBusinessApplication.getId());
        System.out.println("The added node1 ID is : " + addedNode.getId());
        System.out.println("The added node2 ID is : " + addedNode2.getId());


    }
    private static void createBusinessApplication(UcmdbService service, String baName) {

        final TopologyUpdateService topologyUpdateService = service.getTopologyUpdateService();
        final TopologyUpdateFactory factory = topologyUpdateService.getFactory();
        final TopologyModificationData addData = factory.createTopologyModificationData();


        final CI addedBusinessApplication = addData.addCI(CIT_NAME_BUSINESSAPPLICATION);
        addedBusinessApplication.setStringProperty(ATTRIBUTE_NAME, baName);
        addedBusinessApplication.setStringListProperty("contextmenu", new String[]{"2"});


        // Add the data to the UCMDB, in case that the object exists - it will be updated
        topologyUpdateService.create(addData, CreateMode.UPDATE_EXISTING);
        // We print the node ID from the UCMDB.
        System.out.println("The added app ID is : " + addedBusinessApplication.getId());



    }


    private static void createNodeToDatabase(UcmdbService service, String nodeName, String dbName) {

        final TopologyUpdateService topologyUpdateService = service.getTopologyUpdateService();
        final TopologyUpdateFactory factory = topologyUpdateService.getFactory();
        final TopologyModificationData addData = factory.createTopologyModificationData();

        // Create a CI of type node.
        final CI addedNode = addData.addCI(CIT_NAME_HOST);
        addedNode.setStringProperty(ATTRIBUTE_NAME, nodeName);

        final CI addedDatabase = addData.addCI(CIT_NAME_DATABASE);
        addedDatabase.setStringProperty(ATTRIBUTE_PRODUCT_NAME, "maxdb");
        addedDatabase.setStringProperty(ATTRIBUTE_NAME, dbName);


        addData.addRelation(CIT_NAME_COMPOSITION, addedNode, addedDatabase);
        // Add the data to the UCMDB, in case that the object exists - it will be updated
        topologyUpdateService.create(addData, CreateMode.UPDATE_EXISTING);
        // We print the node ID from the UCMDB.
        System.out.println("The added node ID is : " + addedNode.getId());
        System.out.println("The added db ID is : " + addedDatabase.getId());

    }

    private static void createNodeToSNMP(UcmdbService service, String nodeName, String snmpName) {

        final TopologyUpdateService topologyUpdateService = service.getTopologyUpdateService();
        final TopologyUpdateFactory factory = topologyUpdateService.getFactory();
        final TopologyModificationData addData = factory.createTopologyModificationData();

        // Create a CI of type node.
        final CI addedNode = addData.addCI(CIT_NAME_HOST);
        addedNode.setStringProperty(ATTRIBUTE_NAME, nodeName);

        final CI addedSNMP = addData.addCI(CIT_NAME_SNMP);
        addedSNMP.setStringProperty(ATTRIBUTE_NAME, snmpName);
        addedSNMP.setStringProperty(ATTRIBUTE_PRODUCT_NAME, "maxdb");


        addData.addRelation(CIT_NAME_COMPOSITION, addedNode, addedSNMP);
        // Add the data to the UCMDB, in case that the object exists - it will be updated
        topologyUpdateService.create(addData, CreateMode.UPDATE_EXISTING);
        // We print the node ID from the UCMDB.
        System.out.println("The added node ID is : " + addedNode.getId());
        System.out.println("The added db snmp is : " + addedSNMP.getId());

    }


    private static void createNodeToIp(UcmdbService service, String nodeName, String ip) {

        final TopologyUpdateService topologyUpdateService = service.getTopologyUpdateService();
        final TopologyUpdateFactory factory = topologyUpdateService.getFactory();
        final TopologyModificationData addData = factory.createTopologyModificationData();

        // Create a CI of type node.
        final CI addedNode = addData.addCI(CIT_NAME_HOST);
        addedNode.setStringProperty(ATTRIBUTE_NAME, nodeName);

        final CI addedIp = addData.addCI(CIT_NAME_IP);
        addedIp.setStringProperty(ATTRIBUTE_NAME, ip);
        addedIp.setStringProperty(ATTRIBUTE_PROBE_NAME, "SGDLITVM0316");


        addData.addRelation(CIT_NAME_BA_NODE_LINK, addedNode, addedIp);
        // Add the data to the UCMDB, in case that the object exists - it will be updated
        topologyUpdateService.create(addData, CreateMode.UPDATE_EXISTING);
        // We print the node ID from the UCMDB.
        System.out.println("The added node ID is : " + addedNode.getId());
        System.out.println("The added ip ID is : " + addedIp.getId());

    }

    private static void createNodeIpInterface(UcmdbService service, String nodeName, String ip, String interfaceCi) {

        final TopologyUpdateService topologyUpdateService = service.getTopologyUpdateService();
        final TopologyUpdateFactory factory = topologyUpdateService.getFactory();
        final TopologyModificationData addData = factory.createTopologyModificationData();

        // Create a CI of type node.
        final CI addedNode = addData.addCI(CIT_NAME_HOST);
        addedNode.setStringProperty(ATTRIBUTE_NAME, nodeName);

        final CI addedIp = addData.addCI(CIT_NAME_IP);
        addedIp.setStringProperty(ATTRIBUTE_NAME, ip);

        final CI addedInterface = addData.addCI(CIT_NAME_INTERFACE);
        addedInterface.setStringProperty(ATTRIBUTE_INTERFACENAME, interfaceCi);

        addData.addRelation(CIT_NAME_BA_NODE_LINK, addedNode, addedIp);
        addData.addRelation(CIT_NAME_COMPOSITION, addedNode, addedInterface);
        addData.addRelation(CIT_NAME_BA_NODE_LINK, addedInterface, addedIp);
        // Add the data to the UCMDB, in case that the object exists - it will be updated
        topologyUpdateService.create(addData, CreateMode.UPDATE_EXISTING);
        // We print the node ID from the UCMDB.
        System.out.println("The added node ID is : " + addedNode.getId());
        System.out.println("The added ip ID is : " + addedIp.getId());

    }
}
