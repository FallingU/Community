package life.fallingu.commuity.commuity.exception;

public class CustomizeException extends RuntimeException{
    ICustomizeErrorCode customizeErrorCode;
    public CustomizeException(ICustomizeErrorCode customizeErrorCode) {
        this.customizeErrorCode = customizeErrorCode;
    }

    @Override
    public String getMessage() {
        return customizeErrorCode.getMessage();
    }

    public ICustomizeErrorCode getCustomizeErrorCode() {
        return customizeErrorCode;
    }

    public Integer getCode(){
        return customizeErrorCode.getCode();
    }
}
