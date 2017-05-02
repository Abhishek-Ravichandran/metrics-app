package com.metricsapp.storage;

import com.sleepycat.persist.model.Persistent;

@Persistent
public class Heap
{
    private Double[] Heap;
    private int size;
    private int maxsize;
 
    private static final int FRONT = 1;
 
    public Heap()
    {
        this.maxsize = 1024;
        this.size = 0;
        
        Heap = new Double[this.maxsize + 1];
        Heap[0] = Double.NEGATIVE_INFINITY;
    }
    
 
    private int parent(int pos)
    {
        return pos / 2;
    }
 
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
 
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
 
    private boolean isLeaf(int pos)
    {
        if (pos >  (size / 2)  &&  pos <= size)
        { 
            return true;
        }
        return false;
    }
 
    private void swap(int fpos, int spos)
    {
        Double tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }
 
    private void minHeapify(int pos)
    {
        
        if (!isLeaf(pos))
        {   
            if (Heap[pos] > Heap[leftChild(pos)] || Heap[pos] > Heap[rightChild(pos)])
            {
                
                if (Heap[leftChild(pos)] < Heap[rightChild(pos)])
                {   
                    
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                } else
                {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }
 
    public void insert(Double element)
    {
        size += 1;
        Heap[size] = element;
        int current = size;
        
        while (Heap[current] < Heap[parent(current)])
        {
            swap(current,parent(current));
            current = parent(current);
        }	
    }
 
    public void minHeap()
    {
        for (int pos = (size / 2); pos >= 1 ; pos--)
        {
            minHeapify(pos);
        }
    }
 
    public Double remove()
    {
        Double popped = Heap[FRONT];
        Heap[FRONT] = Heap[size--]; 
        minHeapify(FRONT);
        return popped;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public Double peek() {
        return this.Heap[FRONT];
    }
    
    public String printHeap() {
        
        String str = "";

        for(int i = 1; i < size() + FRONT; i++) {
            str += Double.toString(this.Heap[i]) + " ";
        }
        
        return str;
    }
   
}