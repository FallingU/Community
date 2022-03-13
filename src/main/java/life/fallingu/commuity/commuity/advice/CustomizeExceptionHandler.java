package life.fallingu.commuity.commuity.advice;

import com.alibaba.fastjson.JSON;
import life.fallingu.commuity.commuity.dto.ResultDTO;
import life.fallingu.commuity.commuity.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义异常处理类
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    /**
     * · 处理已预知异常
     * · 使用一个枚举类来存放错误代码和错误信息，自定义一个通用的异常类继承来自RuntimeException，
     * 通过传入不同的枚举类对象来实现，错误message和错误code的不同，防止了类爆炸（写过多的异常类），在代码逻辑中判断出现了异常则抛出自定义的异常并传入相应的枚举对象，在controllerAdvice
     * 标识的异常处理类中，通过ExceptionHandler标识了可处理该自定义异常，具体的异常处理分为两种，
     * 如果用户发送的是异步请求，即当前的contentType是application/json（即将错误信息包装成一个实体对
     * 象，返回给用户，此时不进行页面跳转），第二种是将异常信息放入model中跳转到错误页面，显示异
     * 常信息给用户。
     * @param e
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(CustomizeException.class)
    String handler(Exception e,
                   Model model,
                   HttpServletRequest request,
                   HttpServletResponse response){
        String contentType = request.getContentType();
        System.out.println(contentType);
        if("application/json".equals(contentType)){
            e = (CustomizeException)e;
            ResultDTO resultDTO = ResultDTO.errOf(((CustomizeException) e).getCustomizeErrorCode());
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;
        }else {
            model.addAttribute("message", e.getMessage());
        }
        return "error";
    }
}
