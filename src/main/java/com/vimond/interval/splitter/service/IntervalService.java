package com.vimond.interval.splitter.service;

import com.vimond.interval.splitter.Models.Interval;
import org.springframework.stereotype.Service;

@Service
public interface IntervalService {
    String ReadAndExecute(Interval interval) throws Exception;
}
