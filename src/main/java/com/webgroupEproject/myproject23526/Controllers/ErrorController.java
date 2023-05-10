package com.webgroupEproject.myproject23526.Controllers;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", "An error occurred: " + ex.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ModelAndView handleNotFoundException(ChangeSetPersister.NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", "Resource not found: " + ex.getMessage());
        modelAndView.setViewName("not-found");
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", "Access denied: " + ex.getMessage());
        modelAndView.setViewName("access-denied");
        return modelAndView;
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class})
    public ModelAndView handleMediaTypeException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", "Unsupported media type: " + ex.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationException(MethodArgumentNotValidException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", "Validation error: " + ex.getMessage());
        modelAndView.setViewName("validation-error");
        return modelAndView;
    }

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

    @RequestMapping("/not-found")
    public String handleNotFound() {
        return "not-found";
    }

    @RequestMapping("/access-denied.html")
    public String handleAccessDenied() {
        return "access-denied";
    }

    @RequestMapping("/validation-error")
    public String handleValidationError() {
        return "validation-error";
    }

}

