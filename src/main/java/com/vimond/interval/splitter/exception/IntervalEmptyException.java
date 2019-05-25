package com.vimond.interval.splitter.exception;


public class IntervalEmptyException extends Exception {



    public IntervalEmptyException(String message) {
        super(message);
    }

    public String  getExceptionMessage() {
        return  getMessage();
    }
}