package com.lmwis.datachecker.computer.net.proxy.pipe.bean;

import com.lmwis.datachecker.computer.net.proxy.pipe.enumtype.PipeEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hudaming
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PipeEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	private PipeEventType type;
	private String desc;
	private long time;
}
