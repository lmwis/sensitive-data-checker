package com.lmwis.datachecker.computer.net.console.http.vo.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ConsoleConfigVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean httpsProxy;
	
	private boolean mock;
}
