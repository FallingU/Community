package life.fallingu.commuity.commuity.advice;

import life.fallingu.commuity.commuity.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 自定义异常处理类
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(CustomizeException.class)
    String handler(Exception e,
                   Model model){
        model.addAttribute("message",e.getMessage());
        return "error";
    }
}
