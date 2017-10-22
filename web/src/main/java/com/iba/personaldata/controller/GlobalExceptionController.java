package com.iba.personaldata.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpSession;

/**
 * Created by test on 22.10.2017.
 */
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex, HttpSession httpSession) {
        httpSession.setAttribute("error", ex);
        return "error";
    }

    @ExceptionHandler(DataAccessException.class)
    public String handleCustomException(DataAccessException ex, HttpSession httpSession) {
        httpSession.setAttribute("error", ex);
        return "error";
    }

}
