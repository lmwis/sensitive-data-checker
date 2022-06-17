package com.lmwis.datachecker.computer.net.console.http.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class JmitmSessionListVO implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String sessionId;
	// 协议
	private String protocol;
	// uri
	private String uri;
	// 
	private String responseCode;
}
