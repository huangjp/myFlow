package web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.IFlowInstanceService;
import service.IFlowService;
import util.BaseController;
import entity.Flow;
import entity.FlowConnector;
import entity.Process;
import entity.ProcessOperator;
import entity.User;

@Controller
public class FlowControl extends BaseController {
	
	@Autowired
	private IFlowService flowService;
	
	@Autowired
	private IFlowInstanceService instanceService;
	
	@RequestMapping(value="/saveFlow.do",method = RequestMethod.POST)
	public @ResponseBody String saveFlow(Flow flow, List<FlowConnector> uses) {
		Long id = this.flowService.saveFlow(flow, uses);
		return getJson(id);
	}
	
	@RequestMapping(value="/getFlows.do",method=RequestMethod.POST)
	public @ResponseBody String getFlows(Flow flow) {
		List<Flow> flows = this.flowService.getFlows(flow);
		return getJson(flows);
	}
	
	@RequestMapping(value="/updateFlow.do",method=RequestMethod.POST)
	public @ResponseBody String updateFlow(Flow flow) {
		boolean bool = this.flowService.updateFlow(flow);
		return getJson(bool);
	}
	
	@RequestMapping(value="/showFlow.do", method = RequestMethod.POST)
	private @ResponseBody String showFlow(Process process) {
		Object object = this.flowService.getFlowBusinessVo(process);
		return getJson(object);
	}
	
	@RequestMapping(value="/driveFlow.do", method = RequestMethod.POST)
	private @ResponseBody String driveFlow(ProcessOperator operator) {
		if("同意".equals(operator.getOperatorDesc())) {
			operator.setLinkCondition("next");
		} else if("驳回".equals(operator.getOperatorDesc())) {
			operator.setLinkCondition("reject");
		} else if("取消".equals(operator.getOperatorDesc())) {
			operator.setLinkCondition("cancel");
		} 
		boolean bool = this.instanceService.driveFlowInstance(operator);
		return getJson(bool);
	}
	
	@RequestMapping(value="/deleteFlow.do",method=RequestMethod.POST)
	public @ResponseBody String deleteFlow(Flow flow) {
		boolean bool = this.flowService.deleteFlow(flow);
		return getJson(bool);
	}
	
	@RequestMapping(value="/myToDo.do",method = RequestMethod.POST)
	public @ResponseBody String showToDo(User user) {
		Object object = this.instanceService.myToDo(user);
		return getJson(object);
	}
	
	@RequestMapping(value="/ccMe.do",method = RequestMethod.POST)
	public @ResponseBody String ccMe(User user) {
		Object object = this.instanceService.ccMe(user);
		return getJson(object);
	}
	
	@RequestMapping(value="/iCreate.do",method = RequestMethod.POST)
	public @ResponseBody String iCreate(User user) {
		Object object = this.instanceService.iCreate(user);
		return getJson(object);
	}
	
	@RequestMapping(value="/passMe.do",method = RequestMethod.POST)
	public @ResponseBody String passMe(User user) {
		Object object = this.instanceService.passMe(user);
		return getJson(object);
	}
}
