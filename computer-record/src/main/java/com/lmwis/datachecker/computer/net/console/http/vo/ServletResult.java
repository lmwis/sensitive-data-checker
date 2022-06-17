package com.lmwis.datachecker.computer.net.console.http.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServletResult<T> {

	private int code;
	private String errorMessage;
	private T data;
	
	public ServletResult(T data) {
		this.data = data;
	}
}
