package com.dating.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRequest extends BaseRequest implements Serializable {
	private String sex;
	private int start_age;
	private int end_age;
	private String location;
}
