package sm.school.Controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String handlerAccessDeniedException() {
        return "redirect:/accessBlock";
    }

    @ExceptionHandler(Exception.class)
    public String handlerGeneralException() {
        return "redirect:/errorPage";
    }
}
