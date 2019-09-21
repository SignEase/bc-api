package com.ichaoj.sxq.client.enums;

/**
 * @author fan
 * @date 2018-11-30 14:20
 */
public enum  CertTypeEnum {

    IDENTITY_CARD("ID","身份证"),
    INSTITUTION_CODE("INSTITUTION_CODE","组织机构代码证"),
    BUSINESS_LICENCE("BUSINESS_LICENCE","营业执照");

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final String message;

    CertTypeEnum(String code, String message) {
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
