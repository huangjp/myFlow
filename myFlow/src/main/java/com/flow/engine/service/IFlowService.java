package com.flow.engine.service;

import java.util.List;

import com.flow.engine.entity.Flow;
import com.flow.engine.entity.FlowConnector;
import com.flow.engine.entity.Process;
import com.flow.engine.entity.vo.FlowBusinessVo;


public interface IFlowService {
	
	Long saveFlow(Flow flow, List<FlowConnector> uses);
	
	boolean deleteFlow(Flow flow);
	
	boolean updateFlow(Flow flow);
	
	Flow getFlow(Flow flow);
	
	List<Flow> getFlows(Flow flow);
	
	FlowBusinessVo getFlowBusinessVo(Process process);
}
