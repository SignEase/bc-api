package com.ichaoj.sxq.client.compoment;

/**
 * @author fan
 * @date 2018-12-12 15:20
 */
public class ResultInfo<T> extends ResultBase {
    public final static int OK = 200;

    public final static int NO_PERMISSION = 4003;


    private static final long serialVersionUID = 5780174532965843946L;
    /**
     * 返回的数据
     */
    private T data;

    private Integer code;


    public ResultInfo() {
    }

    public ResultInfo(T data, Integer code, boolean status, String message) {
        this.setSuccess(status);
        this.data = data;
        this.code = code;
        this.setMessage(message);
    }

    public ResultInfo(Integer code, boolean success, String message) {
        this.code = code;
        this.setSuccess(success);
        this.setMessage(message);
    }

    public ResultInfo(boolean success, String message) {
        this.setSuccess(success);
        this.setMessage(message);
    }

    public ResultInfo(T data, boolean status, String message) {
        this.setSuccess(status);
        this.data = data;
        this.setMessage(message);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static ResultInfo success(Object data) {

        return new ResultInfo(data, OK, true, "");
    }
    public static ResultInfo success(String m) {

        return new ResultInfo(null, OK, true, m);
    }

    public static ResultInfo success(Object data, String message) {

        return new ResultInfo(data, OK, true, message);
    }

    public static ResultInfo success(Object data, Integer code) {

        return new ResultInfo(data, code, true, "");
    }

    public static ResultInfo error(String message) {
        return new ResultInfo(false, message);
    }

    public static ResultInfo error(String message, int code) {
        return new ResultInfo("",code, false, message);
    }
    /**
     * 没有权限
     *
     * @param message 提示信息
     * @return
     */
    public static ResultInfo noPermission(String message) {
        return new ResultInfo(NO_PERMISSION, false, message);
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "data=" + this.data +
                ", code=" + this.code +
                "} " + super.toString();
    }
}
