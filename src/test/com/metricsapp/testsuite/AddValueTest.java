package test.com.metricsapp.testsuite;

import junit.framework.TestCase;
import com.metricsapp.storage.DBWrapper;

public class AddValueTest extends TestCase {
    
    public void testA() {
        
        DBWrapper.deleteMetric("testmetric");
        DBWrapper.insertMetric("testmetric");
        DBWrapper.addValue("testmetric", 4.0d);
        DBWrapper.addValue("testmetric", 2.0d);
        DBWrapper.addValue("testmetric", 6.0d);
        
        assertEquals(true, DBWrapper.printMetric("testmetric").trim().equals("4.0 2.0 6.0"));
  	}
}
