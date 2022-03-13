package life.fallingu.commuity.commuity.dto;

import life.fallingu.commuity.commuity.exception.CustomizeErrorCode;
import life.fallingu.commuity.commuity.exception.ICustomizeErrorCode;

public class ResultDTO<T>{
    private Integer code;
    private String message;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResultDTO errOf(ICustomizeErrorCode errorCode){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage(errorCode.getMessage());
        resultDTO.setCode(errorCode.getCode());
        return resultDTO;
    }

    public static ResultDTO oKOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");

        return resultDTO;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static<T> ResultDTO oKOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
