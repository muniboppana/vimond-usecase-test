package com.vimond.interval.splitter.service;

import com.vimond.interval.splitter.Models.IntervalSet;


import java.util.Comparator;

public class IntervalSetComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        IntervalSet i1 = (IntervalSet) o1;
        IntervalSet i2 = (IntervalSet) o2;
        return i1.getFirst() - i2.getFirst();
    }
}
