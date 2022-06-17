package com.lmwis.datachecker.computer.net.console.http.service;

import com.lmwis.datachecker.computer.net.console.common.chain.PipeManagerInvokeChain;
import com.lmwis.datachecker.computer.net.console.http.helper.ConsoleHelper;
import com.lmwis.datachecker.computer.net.console.http.vo.JmitmPipeDetailVO;
import com.lmwis.datachecker.computer.net.console.http.vo.JmitmPipeListQueryVO;
import com.lmwis.datachecker.computer.net.console.http.vo.JmitmPipeListVO;
import com.lmwis.datachecker.computer.net.proxy.facade.PipeContext;
import com.lmwis.datachecker.computer.net.proxy.pipe.enumtype.PipeStatus;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class PipeService {
	
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

	public List<JmitmPipeListVO> list(JmitmPipeListQueryVO queryVo) {
		Collection<PipeContext> all = PipeManagerInvokeChain.getAll();
		List<JmitmPipeListVO> requestList = new ArrayList<>();
		all.forEach(item -> {
			if (queryVo.isActive() && item.getCurrentStatus() == PipeStatus.Closed) {
				 return ;
			}
			requestList.add(ConsoleHelper.parse2WtPipeListVO(item));
		});
		return requestList;
	}

	public JmitmPipeDetailVO getById(Long id) {
		PipeContext pipe = PipeManagerInvokeChain.getById(id);
		if (pipe == null) {
			return new JmitmPipeDetailVO();
		}
		JmitmPipeDetailVO detailVo = new JmitmPipeDetailVO();
		List<Map<String, String>> pipeEventMapList = new ArrayList<>();
		pipe.getEventList().forEach(item -> {
			Map<String, String> map = new HashMap<>();
			map.put("type", item.getType().toString());
			map.put("time", DATE_TIME_FORMATTER.format(new Date(item.getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
			map.put("desc", item.getDesc());
			pipeEventMapList.add(map);
		});
		detailVo.setPipeEvent(pipeEventMapList );
		return detailVo;
	}
	
}
