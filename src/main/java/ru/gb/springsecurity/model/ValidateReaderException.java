package ru.gb.springsecurity.model;

public class ValidateReaderException extends RuntimeException{
    public ValidateReaderException(String massage){
        super(massage);
    }
}
