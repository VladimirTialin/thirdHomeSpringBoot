package ru.gb.springdemo.model;

public class ValidateReaderException extends RuntimeException{
    public ValidateReaderException(String massage){
        super(massage);
    }
}
