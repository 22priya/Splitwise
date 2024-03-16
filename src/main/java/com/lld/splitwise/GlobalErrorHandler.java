package com.lld.splitwise;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalErrorHandler{

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletRequest request,Exception e){
        System.out.println(e.getMessage());
        System.out.println(e.getStackTrace());
    }
}
