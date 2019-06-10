package com.example.demo.Configuration;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception e) {
        if (e != null) e.printStackTrace();

        Map<String, Object> r = new HashMap<>();

        Map<String, Object> message = new HashMap<>();
        message.put("WARNING", "Oh snap! System handling failure.");

        r.put("status", "FAILED");
        r.put("message", message);

        return r;
    }
}
