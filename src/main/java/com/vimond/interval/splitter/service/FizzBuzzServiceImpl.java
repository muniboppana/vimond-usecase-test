package com.vimond.interval.splitter.service;

import com.vimond.interval.splitter.constants.Constants;
import org.springframework.stereotype.Service;

@Service
public class FizzBuzzServiceImpl implements FizzBuzzService{
    public String Execute(){
        StringBuffer result = new StringBuffer();
        for(int index=1; index<=100; index++)
        {
            boolean fizz = index % 3 == 0;
            boolean buzz = index % 5 == 0;
            result.append(System.lineSeparator()).append(fizz ? buzz ? Constants.FIZZBUZZ : Constants.FIZZ : buzz ? Constants.BUZZ  : String.valueOf(index));
        }
        return result.toString();
    }
}
