package com.example.bumblebee.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("http://localhost:8080")
public class CustomErrorController extends AbstractMethodError{
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Retrieve error information from the request
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // Customize the error page based on the status code
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            System.out.println( "error/404");
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            System.out.println( "error/500");
        } else {
            System.out.println("error/generic");
        }
        return null;
    }
}
