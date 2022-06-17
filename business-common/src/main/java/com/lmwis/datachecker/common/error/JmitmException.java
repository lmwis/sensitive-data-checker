package com.lmwis.datachecker.common.error;

public class JmitmException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JmitmException(String msg) {
		super(msg);
	}

	public JmitmException(String msg, Throwable error) {
		super(msg, error);
	}
}
