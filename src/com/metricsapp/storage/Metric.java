package com.metricsapp.storage;

import java.util.PriorityQueue;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class Metric {
    
    @PrimaryKey
    private String name;
    
    private Double min;
    private Double max;
    private Double total;
    
    private Heap minheap;

    private Heap maxheap;
    
    public Metric() { }
    
    public Metric(String name) {
        this.name = name;
    
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
        total = 0d;
        minheap = new Heap();
        maxheap = new Heap();
    }
    
    public void addValue(Double val) {
        
        if(val > max) {
            max = val;
        }
        
        if(val < min) {
            min = val;
        }
        
        total += val;
        
        if(minheap.isEmpty() && maxheap.isEmpty()) {
            minheap.insert(val);
            return;
        }
        
        Double median = getMedian();
        
        if(val >= median) {
            minheap.insert(val);
            
            if(minheap.size() == maxheap.size() + 2) {
                maxheap.insert(-minheap.remove());
            }
        } else {
            maxheap.insert(-1.0d * val);
            
            if(maxheap.size() == minheap.size() + 2) {
                minheap.insert(-maxheap.remove());
            }
        }
        
    }
    
    public boolean isEmpty() {
        return (minheap.size() == 0 && maxheap.size() == 0);
    }
    
    public Double getMin() {
        return min;
    }
    
    public Double getMax() {
        return max;
    }
    
    public Double getMean() {
        return total / (minheap.size() + maxheap.size());
    }
    
    public Double getMedian() {
        
        if(minheap.size() > maxheap.size()) {
            return minheap.peek();
        } else if(maxheap.size() > minheap.size()) {
            return -maxheap.peek();
        } else {
            return (minheap.peek() - maxheap.peek()) / 2.0d;
        }
    }
    
    public String getHeap() {
        return maxheap.printHeap() + " | " + minheap.printHeap();
    }
    
    
}