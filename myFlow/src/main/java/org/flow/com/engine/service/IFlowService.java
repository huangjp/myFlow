package service;

import java.util.List;

import entity.Flow;
import entity.FlowConnector;
import entity.Process;
import entity.vo.FlowBusinessVo;


public interface IFlowService {
	
	Long saveFlow(Flow flow, List<FlowConnector> uses);
	
	boolean deleteFlow(Flow flow);
	
	boolean updateFlow(Flow flow);
	
	Flow getFlow(Flow flow);
	
	List<Flow> getFlows(Flow flow);
	
	FlowBusinessVo getFlowBusinessVo(Process process);
}
