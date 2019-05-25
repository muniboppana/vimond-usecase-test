package com.vimond.interval.splitter.Models;

public class IntervalSet {

    private int first;
    private int last;

    public IntervalSet() {

    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    /**
     *
     * @param first
     * @param last
     */
    public IntervalSet(final int first , final int last) {
        this.first = first ;
        this.last = last ;
    }

    @Override
    public String toString(){
        return ""+this.getFirst()+"-"+this.getLast()+"";
    }
}
