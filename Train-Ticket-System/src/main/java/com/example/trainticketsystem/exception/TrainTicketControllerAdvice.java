package com.example.trainticketsystem.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TrainTicketControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        ex.printStackTrace();
        model.addAttribute("errorMessage", "Failed to purchase ticket due to invalid input.");
        return "error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        ex.printStackTrace();
        model.addAttribute("errorMessage", "Failed to purchase ticket due to an unexpected error.");
        return "error";
    }
}
