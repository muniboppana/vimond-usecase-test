package com.vimond.interval.splitter.exception;


public class IntervalSameException  extends Exception {



    public  IntervalSameException(String message) {
        super(message);
    }

    public String  getExceptionMessage() {
        return  getMessage();
    }
}