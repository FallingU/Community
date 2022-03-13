package life.fallingu.commuity.commuity.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public static boolean isExist(Integer type){
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if(value.getType()==type){
                return true;
            }
        }
        return false;
    }
}
