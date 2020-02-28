package com.dating.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginCreated;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastCreated;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginModified;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastModified;
	private String status;
	private String creator;
	private String operator;
	private int PageSize = 10000;
	private int PageNum = 1;
	private long startIndex;
}
