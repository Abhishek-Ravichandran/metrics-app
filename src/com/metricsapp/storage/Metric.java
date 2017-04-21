package com.metricsapp.storage;

import java.util.ArrayList;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class Metric {
    
    @PrimaryKey
    private String name;
    
    private ArrayList<Double> values;
    
    public Metric() { }
    
    public Metric(String name) {
        this.name = name;
        this.values = new ArrayList<>();
    }
    
    public void addValue(Double val) {
        this.values.add(val);
    }
    
    public ArrayList<Double> getValues() {
        return this.values;
    }
    
}