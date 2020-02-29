package com.dating.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口返回实体
 * @author elvis
 */
@Getter
@Setter
public class BaseResult {

	private int code;

	private String message = "";

	private Object data;

    public BaseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}