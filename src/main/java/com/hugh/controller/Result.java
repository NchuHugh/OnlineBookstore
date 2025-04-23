package com.hugh.controller;

import java.io.Serializable;

/**
 * 统一响应结果类
 */
public class Result implements Serializable {
    private Boolean success;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(true, "操作成功");
    }

    public static Result success(String message) {
        return new Result(true, message);
    }

    public static Result success(Object data) {
        return new Result(true, "操作成功", data);
    }

    public static Result success(String message, Object data) {
        return new Result(true, message, data);
    }

    public static Result error() {
        return new Result(false, "操作失败");
    }

    public static Result error(String message) {
        return new Result(false, message);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
