package com.dating.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 接口返回实体
 * @author Feng
 */
@Getter
@Setter
public class BaseResult {

    private boolean status;

    private String message = "";

    private Object data;

    public BaseResult(){}

    public BaseResult(boolean status) {
        this.status = status;
    }

    public BaseResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResult(boolean status, Object data) {
        this.status = status;
        this.data = data;
    }

    public BaseResult(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BaseResult success(String message) {
        this.status = true;
        this.message = message;
        return this;
    }

    public BaseResult failed(String message) {
        this.status = false;
        this.message = message;
        return this;
    }



}