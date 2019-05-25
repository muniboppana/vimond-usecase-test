package com.vimond.interval.splitter.utils;
import com.vimond.interval.splitter.Models.IntervalSet;
import com.vimond.interval.splitter.constants.ExceptionMessages;
import com.vimond.interval.splitter.constants.Constants;
import com.vimond.interval.splitter.exception.ParserException;
import com.vimond.interval.splitter.service.IntervalSetComparator;

import java.util.*;
import java.util.stream.Collectors;

public class IntervalUtil {

    /**
     * @param intervals
     * @return
     */
    public static List<IntervalSet> mergeOverlappingIntervals(List<IntervalSet> intervals) {


        //intervals size one or empty then return
        if (intervals == null || intervals.size() == 0 || intervals.size() == 1) {
            return intervals;
        }

        List<IntervalSet> resultList = new ArrayList<IntervalSet>();

        //sorting the intervals order wise
        Collections.sort(intervals, new IntervalSetComparator());

        int index = 0;

        IntervalSet firstIntervalSet = intervals.get(index);

        for (int j = 1; j < intervals.size(); j++) {
            IntervalSet tempIntervalSet = intervals.get(j);
            if (tempIntervalSet.getFirst() <= firstIntervalSet.getLast()) {
                //firstIntervalSet.getLast() = Math.max(firstIntervalSet.getLast(), tempIntervalSet.getLast());
                firstIntervalSet.setLast(Math.max(firstIntervalSet.getLast(), tempIntervalSet.getLast()));
            } else {
                resultList.add(firstIntervalSet);
                firstIntervalSet = tempIntervalSet;
            }
        }

        resultList.add(firstIntervalSet);
        return resultList;
    }


    /**
     *
     * @param includeIntervalSets
     * @param exclIntervalSets
     * @return
     */
    public static List<IntervalSet> getOutputIntervalsMap(List<IntervalSet> includeIntervalSets, List<IntervalSet> exclIntervalSets) {

        if(exclIntervalSets == null || exclIntervalSets.isEmpty() || exclIntervalSets.size() == 0) {
            return includeIntervalSets;
        }

        Map<Integer, String> inclExclIntervals  = combineIncludeExcludeIntervalSets(includeIntervalSets,exclIntervalSets);

        return getResultIntervals(inclExclIntervals).stream().
                filter(x -> x.getFirst() != x.getLast()).collect(Collectors.toList());

    }

    /**
     *
     * @param inclExclIntervals
     * @return
     */
    private static List<IntervalSet> getResultIntervals(Map<Integer, String> inclExclIntervals) {

        List <IntervalSet>  resultList = new ArrayList<IntervalSet>();

        //initialize variable
        boolean isInterval = false;
        boolean isGap = false;
        int intervalStart = 0;

        for(Map.Entry<Integer,String> entryMap : inclExclIntervals.entrySet()) {
            Integer key = entryMap.getKey();
            String value = entryMap.getValue();

            switch (value)  {
                case Constants.START:
                    if (!isGap) {
                        intervalStart = key;
                    }
                    isInterval = true;
                    break;

                case Constants.END:
                    if (!isGap) {
                        resultList.add(new IntervalSet(intervalStart, entryMap.getKey()));
                    }
                    isInterval = false;
                    break;
                case Constants.EXCLUDE_START:
                    if(isInterval) {
                        resultList.add(new IntervalSet(intervalStart,entryMap.getKey()-1));
                        isInterval = false;
                        isGap = true;
                        break;
                    }
                case Constants.EXCLUDE_END:

                    if(isGap) {
                        intervalStart = entryMap.getKey()+1;
                        isGap = false;
                        isInterval = true;
                        break;
                    }
            }


        }
        return resultList;

    }
    /**
     *
     * @param includeIntervalSets
     * @param exclIntervalSets
     */
    private static Map<Integer, String> combineIncludeExcludeIntervalSets(List<IntervalSet> includeIntervalSets, List<IntervalSet> exclIntervalSets) {

        Map<Integer, String>    inclExclIntervals = new TreeMap<>();

        for (IntervalSet intervalSet : includeIntervalSets) {
            inclExclIntervals.put(intervalSet.getFirst(), Constants.START);
            inclExclIntervals.put(intervalSet.getLast(), Constants.END);
        }

        for (IntervalSet excludeSet : exclIntervalSets) {

               inclExclIntervals.put(excludeSet.getFirst(), Constants.EXCLUDE_START);

                inclExclIntervals.put(excludeSet.getLast(), Constants.EXCLUDE_END);

        }

        return inclExclIntervals;
    }

    /**
     *
     * @param intervals
     * @return
     */
    public static List<IntervalSet> Parser(String intervals) throws ParserException {
        try
         {
             List<IntervalSet> intervalSets = new ArrayList<IntervalSet>() ;
             String[] tokens = intervals.split(",");
             for (int i = 0; i<tokens.length; i++){
                 String t = tokens[i].trim();
                 String[] t1 = t.split("-");
                 IntervalSet intervalSet = new IntervalSet();
                 if((Integer.parseInt(t1[0]))>(Integer.parseInt(t1[1]))) {
                     intervalSet.setLast(Integer.parseInt(t1[0]));
                     intervalSet.setFirst(Integer.parseInt(t1[1]));
                 }
                 else {
                     intervalSet.setLast(Integer.parseInt(t1[1]));
                     intervalSet.setFirst(Integer.parseInt(t1[0]));
                 }
                 intervalSets.add(intervalSet);
             }
             return intervalSets;
         }catch (Exception e){
             throw new ParserException(ExceptionMessages.INPUT_INTERVALS_PARSING);
         }
     }
}
