package test.com.metricsapp.testsuite;

import java.util.Enumeration;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;
import junit.framework.TestFailure;

import com.metricsapp.storage.DBWrapper;

public class RunAllTests extends TestCase {

  public static Test suite() {
    try {
      @SuppressWarnings("rawtypes")
      Class[] testClasses = {
        GetMinTest.class,
        GetMaxTest.class,
        GetMeanTest.class,
        GetMedianTest.class
      };   
      
      return new TestSuite(testClasses);
    } catch(Exception e){
      e.printStackTrace();
    } 
    
    return null;
  }

  public static void main(String[] args) {
      
    String BDBPath = "../database";
    DBWrapper.init(BDBPath);
	DBWrapper.setupEntityStore();
	
    TestSuite ts = (TestSuite) suite();
    TestResult res = new TestResult();
    ts.run(res);
                
    for (Enumeration<TestFailure> e = res.failures(); e.hasMoreElements(); ) {
      System.out.println(e.nextElement());
    }
                
    System.out.println("TESTS RUN: " + res.runCount());
    System.out.println("TESTS PASSED: " + res.wasSuccessful());
    
    DBWrapper.shutdownEntityStore();
  }
}
