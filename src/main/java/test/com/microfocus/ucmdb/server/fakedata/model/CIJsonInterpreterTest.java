package test.com.microfocus.ucmdb.server.fakedata.model; 

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfocus.ucmdb.server.fakedata.model.CIHolder;
import com.microfocus.ucmdb.server.fakedata.model.CIJsonInterpreter;
import com.microfocus.ucmdb.server.fakedata.model.RelationHolder;
import com.microfocus.ucmdb.server.fakedata.model.TopoHolder;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/** 
* CIJsonInterpreter Tester. 
* 
* @author <Authors name> 
* @since <pre>Jul 5, 2018</pre> 
* @version 1.0 
*/ 
public class CIJsonInterpreterTest { 

@Before
public void before() throws Exception {

} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: loadJson(File file) 
* 
*/ 
@Test
public void testLoadJson() throws Exception { 
    TopoHolder topo = CIJsonInterpreter.loadJson(new File("1.txt"));
    assert topo!= null;
} 

/** 
* 
* Method: writeJson(CIHolder cis, File file) 
* 
*/ 
@Test
public void testWriteJson() throws Exception {
    TopoHolder topo = new TopoHolder();
    CIHolder ci1 = new CIHolder("node", "node");
    ci1.addAttribute_v("name","node");
    topo.addCI(ci1.getName(), ci1);

    CIHolder ci2 = new CIHolder("ip_address", "ip");
    ci2.addAttribute_v("name", "1.1.1.1");
    topo.addCI(ci2.getName(), ci2);

    RelationHolder relation = new RelationHolder();
    relation.setRelationType("containment");
    relation.setEnd1Id("node");
    relation.setEnd2Id("ip");
    topo.addRelation(relation);


    File file = new File("1.txt");
    if(file.exists()){
        file.delete();
    }
    if(file.exists()){
        file.delete();
    }
    CIJsonInterpreter.writeJson(topo, file);

    assert file.exists();

} 


} 
