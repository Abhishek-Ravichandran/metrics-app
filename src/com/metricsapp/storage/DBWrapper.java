package com.metricsapp.storage;

import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig; 
import com.sleepycat.persist.PrimaryIndex;

public class DBWrapper {
    
    private static File envHome;
    
    private static Environment envmnt;
    private static EntityStore entityStore;
    
    private static PrimaryIndex<String, Metric> metricTable;

    private DBWrapper() { }
    
    public static void init(String path) {
        envHome = new File(path);
        
        if (!envHome.exists()) {
            try {
                envHome.mkdir();
            } catch(SecurityException ex) { }
        }
    }

    public static void setupEntityStore() throws DatabaseException {
        
        //setup environment 
        EnvironmentConfig envConfig = new EnvironmentConfig();
        StoreConfig storeConfig = new StoreConfig();
        
        envConfig.setAllowCreate(true);
        storeConfig.setAllowCreate(true);
        
        // Open the environment and entity store
        envmnt = new Environment(envHome, envConfig);
        entityStore = new EntityStore(envmnt, "entityStore", storeConfig);
        
        metricTable = entityStore.getPrimaryIndex(String.class, Metric.class);	
    }
    
    public static void shutdownEntityStore() throws DatabaseException {
        entityStore.close();
        envmnt.close();
    }
    
    public static synchronized boolean metricExists(String name) throws DatabaseException {
        Metric metric = metricTable.get(name);
        if(metric != null)
            return true;
        return false;
    }
    
    // Insert metric into entity store
    public static synchronized boolean insertMetric(String name) throws DatabaseException {
        if(metricExists(name))
            return false;
        Metric metric = new Metric(name);
        metricTable.put(metric);
        entityStore.sync();
        envmnt.sync();
        return true;
    }
    
    public static synchronized boolean deleteMetric(String name) throws DatabaseException {
        if(!metricExists(name))
            return false;
        metricTable.delete(name);
        entityStore.sync();
        envmnt.sync();
        return true;
    }
    
    
    public static synchronized boolean addValue(String name, Double val) throws DatabaseException {
        if(!metricExists(name))
            return false;

        Metric metric = metricTable.get(name);
        metric.addValue(val);
        metricTable.put(metric);
        
        entityStore.sync();
        envmnt.sync();
        return true;
    }
    
    public static synchronized Double getMin(String name) throws DatabaseException {
        if(!metricExists(name))
            return null;
        
        Metric metric = metricTable.get(name);
        
        if(metric.isEmpty())
            return null;
            
        return metric.getMin();
    } 
    
    public static synchronized Double getMax(String name) throws DatabaseException {
        if(!metricExists(name))
            return null;
        
        Metric metric = metricTable.get(name);
        
        if(metric.isEmpty())
            return null;
            
        return metric.getMax();
    }
    
    public static synchronized Double getMean(String name) throws DatabaseException {
        if(!metricExists(name))
            return null;
        
        Metric metric = metricTable.get(name);
        
        if(metric.isEmpty())
            return null;
            
        return metric.getMean();
    }
    
    public static synchronized Double getMedian(String name) throws DatabaseException {
        if(!metricExists(name))
            return null;
        
        Metric metric = metricTable.get(name);
        
        if(metric.isEmpty())
            return null;
            
        return metric.getMedian();
    }
    
    public static synchronized String printMetric(String name) throws DatabaseException {
        if(!metricExists(name))
            return null;
        
        Metric metric = metricTable.get(name);
        
        if(metric.isEmpty())
            return null;
            
        return metric.getHeap();
    }
    
    
}