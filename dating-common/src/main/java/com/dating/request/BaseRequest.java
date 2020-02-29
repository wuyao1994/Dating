package com.dating.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class BaseRequest {
	private String id;
	private String sortField;
	private String sortType;
	private int PageSize = 20;
	private int PageNum = 1;
	private long startIndex;
}
