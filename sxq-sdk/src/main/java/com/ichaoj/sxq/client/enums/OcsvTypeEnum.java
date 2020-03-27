package com.ichaoj.sxq.client.enums;

public enum  OcsvTypeEnum {
    /**
     * 文件数据
     */
    FILE("FILE","文件类型"),
    /**
     * 信息数据
     */
    INFOMATION("INFOMATION","信息类型");

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    OcsvTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
