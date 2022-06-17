package com.lmwis.datachecker.computer.net.console.http.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class JmitmPipeListVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// id
	private String pipeId;
	// 协议
	private String protocol;
	// uri
	private String name;
	//
	private String status;
}
