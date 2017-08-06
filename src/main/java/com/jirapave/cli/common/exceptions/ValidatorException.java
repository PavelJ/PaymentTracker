package com.jirapave.cli.common.exceptions;

public class ValidatorException extends Exception {

    public String message;

    public ValidatorException(String message){
        this.message = message;
    }

    // Overrides Exception's getMessage()
    @Override
    public String getMessage(){
        return message;
    }

}
