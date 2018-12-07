package test.com.microfocus.ucmdb.server.fakedata.topo; 

import com.hp.ucmdb.api.*;
import com.microfocus.ucmdb.server.fakedata.cache.CreatedCICache;
import com.microfocus.ucmdb.server.fakedata.topo.TopoGenerator;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.File;
import java.net.MalformedURLException;

/** 
* TopoGenerator Tester. 
* 
* @author <Authors name> 
* @since <pre>Dec 7, 2018</pre> 
* @version 1.0 
*/ 
public class TopoGeneratorTest { 
    private UcmdbService service;

    @Before
public void before() throws Exception {
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
    service = serviceProvider.connect(credentials,clientContext);
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getInstance() 
* 
*/ 
@Test
public void testGetInstance() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: insertTopologyFromTopoHolder(TopologyUpdateService service, TopoHolder topo) 
* 
*/ 
@Test
public void testInsertTopologyFromTopoHolder() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: clearAll(TopologyUpdateService service) 
* 
*/ 
@Test
public void testClearAll() throws Exception {
    TopoGenerator.getInstance().clearAll(service.getTopologyUpdateService());
    File file = new File(CreatedCICache.filename);
    assert (!file.exists());
} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
@Test
public void testMain() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: generateTopo(Map<String, CIHolder> ciMap, List<RelationHolder> relationList, int topoCount, TopologyModificationData data) 
* 
*/ 
@Test
public void testGenerateTopo() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = TopoGenerator.getClass().getMethod("generateTopo", Map<String,.class, List<RelationHolder>.class, int.class, TopologyModificationData.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
