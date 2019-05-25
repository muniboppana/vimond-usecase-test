package com.vimond.interval.splitter.service;

import com.vimond.interval.splitter.Models.Interval;
import com.vimond.interval.splitter.Models.IntervalSet;
import com.vimond.interval.splitter.constants.ExceptionMessages;
import com.vimond.interval.splitter.exception.IntervalEmptyException;
import com.vimond.interval.splitter.exception.IntervalSameException;
import com.vimond.interval.splitter.utils.IntervalUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntervalServiceImpl implements IntervalService {

    public String ReadAndExecute(Interval interval) throws Exception {

        if(StringUtils.isEmpty(interval.getIncludeIntervals())){
            throw new IntervalEmptyException(ExceptionMessages.INTERVAL_SHOULD_NOT_EMPTY);
        }


        List<IntervalSet> includeIntervalSets = IntervalUtil.Parser(interval.getIncludeIntervals());
        includeIntervalSets = IntervalUtil.mergeOverlappingIntervals(includeIntervalSets);
        if(StringUtils.isEmpty(interval.getExcludeIntervals())){
            return includeIntervalSets.stream().map(IntervalSet::toString).collect(Collectors.joining(","));
        }
        List<IntervalSet> excludeIntervalSets = IntervalUtil.Parser(interval.getExcludeIntervals());

        excludeIntervalSets = IntervalUtil.mergeOverlappingIntervals(excludeIntervalSets);

        if(excludeIntervalSets.size()==1 && excludeIntervalSets.get(0).getFirst() == excludeIntervalSets.get(0).getLast()) {
            throw new IntervalSameException(ExceptionMessages.INTERVAL_SHOULD_NOT_SAME);
        }

        List<IntervalSet> resultIntervals = IntervalUtil.getOutputIntervalsMap(includeIntervalSets, excludeIntervalSets);

        return resultIntervals.stream().map(IntervalSet::toString).collect(Collectors.joining(","));
        }
}

