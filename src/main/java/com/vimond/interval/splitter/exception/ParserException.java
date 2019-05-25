package com.vimond.interval.splitter.exception;


public class ParserException extends Exception {

    public ParserException(String message) {
        super(message);
    }

    public String  getExceptionMessage() {
        return  getMessage();
    }
}