package test.com.metricsapp.testsuite;

import junit.framework.TestCase;
import com.metricsapp.storage.DBWrapper;

public class GetMinTest extends TestCase {
    
    public void testA() {
        
        DBWrapper.deleteMetric("testmetric");
        DBWrapper.insertMetric("testmetric");
        DBWrapper.addValue("testmetric", 4.0d);
        DBWrapper.addValue("testmetric", 2.0d);
        DBWrapper.addValue("testmetric", 6.0d);
        
        assertEquals(true, DBWrapper.getMin("testmetric") == 2.0d);
  	}
}
