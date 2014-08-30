package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import service.IFlowInstanceService;
import base.DaoService;
import entity.Business;
import entity.Condition;
import entity.ExecuteUser;
import entity.Flow;
import entity.FlowAssist;
import entity.FlowInstance;
import entity.FlowInstanceAssist;
import entity.Link;
import entity.LinkHasCondition;
import entity.NotifyUser;
import entity.Process;
import entity.ProcessOperator;
import entity.User;

@Service
public class FlowInstanceService extends DaoService implements IFlowInstanceService {

	@Override
	public List<FlowInstanceAssist> createFlowInstance(Flow flow, 
			List<Link> links, Business business, User user, FlowInstance parentInstance) {
		
		//排除异常
		if(links == null || business == null || user == null) {
			throw new RuntimeException("The link or flow or business or user cannot be empty");
		}
		if(links.size() == 0) {
			throw new RuntimeException("flow can not link");
		}
		
		//创建流程实例
		FlowInstance instance = new FlowInstance();
		instance.setCretaeUserId(user.getId());
		instance.setCurrentLinkId(links.get(0).getId());
		//TODO 后续可考虑与用户交互是否要保存该流程为自定义流程，方便下次使用
		if(flow == null) {
			instance.setFlowId(-1L);//直接创建流程实例，表示其为临时自定义的流程
		} else {
			instance.setFlowId(flow.getId());
		}
		if(links.size() > 1) {
			instance.setNextLinkId(links.get(1).getId());
		} else {
			instance.setNextLinkId(-1L);//表示流程结束
		}
		if(parentInstance != null) {
			instance.setFlowInstanceId(parentInstance.getId());
			parentInstance.setFlowInstanceId(0L);//表示启用子流程，父流程暂停执行
			update(parentInstance);
			Process parentProcess = new Process(parentInstance.getId(), null);
			
			//修改父流程的相关信息
			List<Process> processes = getList(parentProcess);
			if(processes != null && !processes.isEmpty()) {
				Process process = processes.get(processes.size() - 1);
				process.setIsShow(false);//子流程开启，父流程的最后一条状态即关闭显示
				update(process);
				ProcessOperator operator = get(new ProcessOperator(process.getId(), null));
				operator.setUserId(user.getId());
				update(operator);
			} else {
				throw new RuntimeException("Should not be no process state");
			}
		}
		Long instanceId = save(instance);
		instance.setId(instanceId);
		
		Long businessId = save(business);
		business.setId(businessId);
		
		//初始化流程实例的状态
		Process process1 = new Process(instance.getId(), null);
		process1.setCurrentLinkId(links.get(0).getId());
		process1.setIsShow(true);//第一个流程环节默认显示
		process1.setBusinessId(business.getId());
		Long process1Id = save(process1);
		ProcessOperator operator1 = new ProcessOperator(process1Id, null);
		operator1.setUserId(user.getId());
		operator1.setOperatorDesc("提交");
		operator1.setLinkCondition("next");
		save(operator1);
		
		//为下一个环节提前准备好一条待修改的状态信息，方便待办用户 //TODO 待定
		Process process2 = new Process(instance.getId(), null);
		process2.setCurrentLinkId(instance.getNextLinkId());
		process2.setIsShow(false);//第一个流程环节默认显示
		process2.setBusinessId(business.getId());
		process2.setBeforeId(process1Id);
		save(process2);
		
		//绑定流程实例的环节
		List<FlowInstanceAssist> assists = new ArrayList<FlowInstanceAssist>();
		for(Link link : links) {
			FlowInstanceAssist assist = new FlowInstanceAssist();
			assist.setFlowInstanceId(instance.getId());
			assist.setLinkId(link.getId());
			Long assistId = save(assist);
			assist.setId(assistId);
			assists.add(assist);
		}
		
		//设定流程实例中每个环节的条件，只有临时流程需要设置条件
		//TODO 后期完善，此处应该与页面有一个交互后才调用setFlowInstanceLinkCondition（）完成临时流程每个环节的条件绑定
		
		return assists;
	}

	@Override
	public boolean setFlowInstanceLinkCondition(FlowInstanceAssist assist,
			List<Condition> conditions) {
		
		//排除异常
		if(assist == null) {
			throw new RuntimeException("Link is empty");
		}

		//绑定条件
		if(conditions != null) {
			for (Condition condition : conditions) {
				LinkHasCondition lhc = new LinkHasCondition();
				if(condition.isResult()) {
					lhc.setConditionTrueLinkId(condition.getLinkId());
				} else {
					lhc.setConditionFalseLinkId(condition.getLinkId());
				}
				lhc.setConditionId(condition.getId());
				lhc.setFlowInstanceAssistId(assist.getId());
				save(lhc);
			}
		}
		return true;
	}

	@Override
	public boolean driveFlowInstance(ProcessOperator operator) {
		
		//排除异常
		if(operator == null || operator.getProcessId() == null || operator.getUserId() == null) {
			throw new RuntimeException("flow or user or status can not null");
		}
		
		//保存当前用户的操作信息
		save(operator);
		
		//为下一个环节提前准备好一条待修改的状态信息，方便待办用户 //TODO 待定
		Process process = get(new Process(null, operator.getProcessId()));
		FlowInstance instance = get(new FlowInstance(process.getFlowInstanceId(), null));
		Flow flow = get(new Flow(instance.getFlowId()));
		if(flow.getLevel() == 0) {//0表示默认流程
			//TODO 可扩展功能，后续再处理
		}
		String expression = operator.getLinkCondition();
		List<String> expressions = operator.getLinkConditions();
		List<String> exps = new ArrayList<String>();
		if(expression != null) exps.add(expression);
		if(expressions != null && !expressions.isEmpty()) exps.addAll(expressions);
		instance.setCurrentLinkId(instance.getNextLinkId());
		List<LinkHasCondition> conditions = new ArrayList<LinkHasCondition>();
		if(instance.getFlowId() == -1) {//临时流程需要根据流程实例中绑定的环节的对应条件进行判断走向 TODO 后期完善
			FlowInstanceAssist assist = get(new FlowInstanceAssist(instance.getNextLinkId(), instance.getId()));
			conditions = getList(new LinkHasCondition(assist.getId(), null));
		} else {//非临时流程可以从流程附表中获取绑定的环节进行判断走向
			FlowAssist assist = get(new FlowAssist(instance.getNextLinkId(), instance.getFlowId()));
			conditions = getList(new LinkHasCondition(null, assist.getId()));
		}
		List<Boolean> trues = new ArrayList<Boolean>();
//		List<Boolean> falses = new ArrayList<Boolean>();
		for (LinkHasCondition lc : conditions) {
			Condition condition = get(new Condition(lc.getConditionId()));
			for(String string : exps) {
				if(string.equalsIgnoreCase(condition.getExpression())) {
					trues.add(true);
				}
			}
		}
		//TODO 可以为每种设定不同的环节，增加子流程可实现并行与串行的流程，后期完善
		if(trues.size() > 0) {
			instance.setNextLinkId(conditions.get(0).getConditionTrueLinkId());
		} else {
			instance.setNextLinkId(conditions.get(0).getConditionFalseLinkId());
		}
		update(instance);
		
		//修改当前状态为可见
		process.setIsShow(true);
		process.setBeforeId(null);
		update(process);
		
		//设置下一个待办环节的初始状态
		Process process2 = new Process();
		process2.setCurrentLinkId(instance.getNextLinkId());
		if( instance.getNextLinkId() == -1) {
			process2.setIsShow(true);
		} else {
			process2.setIsShow(false);
			process2.setBeforeId(process.getId());
		}
		process2.setId(null);
		process2.setBusinessId(process.getBusinessId());
		process2.setFlowInstanceId(process.getFlowInstanceId());
		save(process2);
		return true;
	}
	
	@Override
	public boolean startFlowInstance(Flow flow, Business business, User user) {
		
		//排除异常
		if(flow == null || business == null || user == null) {
			throw new RuntimeException("flow or user or business can not null");
		}
		flow = get(flow);
		if(flow.getLevel() == null) {
			throw new RuntimeException("A process definition exception");
		}
		
		//取出该流程定义好的环节，TODO 要确保环节是顺序存进去的
		List<FlowAssist> assists = getList(new FlowAssist(null, flow.getId()));
		List<Link> links = new ArrayList<Link>();
		for(FlowAssist assist : assists) {
			links.add(new Link(assist.getLinkId()));
		}
		
		//创建流程实例
		createFlowInstance(flow, links, business, user, null);
		return true;
	}

	@Override
	public boolean finishFlowInstance(FlowInstance instance, User user) {
		instance = get(instance);
		instance.setNextLinkId(-1L);//表示流程终止
		update(instance);
		List<Process> processes = getList(new Process(instance.getId(), null));
		Process process = processes.get(processes.size() - 1);
		if(process.getIsShow()) {
			process.setId(null);
			process.setCurrentLinkId(instance.getNextLinkId());
			Long id = save(process);
			process.setId(id);
		} else {
			process.setIsShow(true);
			update(process);
		}
		ProcessOperator operator = new ProcessOperator(process.getId(), null);
		operator.setUserId(user.getId());
		operator.setOperatorDesc("结束");
		operator.setLinkCondition("cancel");
		save(operator);
		return true;
	}

	@Override
	public List<FlowInstanceAssist> updateFlowInstance(FlowInstance instance,
			List<FlowInstanceAssist> links, User user) {
		//排除异常
		if(instance == null || links == null || user == null || instance.getId() == null) {
			throw new RuntimeException("flow or link or user can not empty");
		}
		//已经完成的流程环节不允许修改
		List<Process> processes = getList(new Process(instance.getId(), null));
		for(FlowInstanceAssist link : links) {
			if(link.getId() == null || link.getLinkId() == null || link.getOldLink() == null) {
				throw new RuntimeException("link id can not empty");
			}
			for(Process process : processes) {
				if(link.getOldLink() == process.getCurrentLinkId()) {
					throw new RuntimeException("Cannot be modified have been completed");
				}
			}
		}
		//TODO 修改权限的控制，后期调整
		Flow flow = get(new Flow(instance.getFlowId()));
		if(flow != null) {
			if(flow.getLevel() == 0 || flow.getLevel() == 1 || flow.getLevel() == 2) {
				throw new RuntimeException("Please contact the founder of process changes");
			}
		}
		//更新
		update(instance);
		for(FlowInstanceAssist link : links) {
			update(link);
		}
		return links;
	}

	@Override
	public boolean updateFlowInstanceLinkCondition(List<LinkHasCondition> conditions) {
		
		//排除异常
		if(conditions == null || conditions.isEmpty()) {
			throw new RuntimeException("condition is empty");
		}

		//修改或者新增条件
		for (LinkHasCondition condition : conditions) {
			if(condition.getId() == null) {
				save(condition);
			} else {
				update(condition);
			}
		}
		return true;
	}

	@Override
	public List<Process> myToDo(User user) {
		List<Process> processes = new ArrayList<Process>();
		List<Link> links = new ArrayList<Link>();
		//TODO 可以通过传用户的群组ID或者岗位ID定位到相应的环节ID，后期考虑调整
		List<ExecuteUser> users = getList(new ExecuteUser(null, user.getId()));
		Long linkId = null;
		for (ExecuteUser eu : users) {
			if(linkId != eu.getLinkId()) {
				links.add(new Link(eu.getLinkId()));
			}
			linkId = eu.getLinkId();
		}
		processes = getProcessesMyToDo(links);
		for(Process process : processes) {
			List<ProcessOperator> list = getList(new ProcessOperator(process.getBeforeId(), null));
			process.setOperators(list);
		}
		return processes;
	}

	@Override
	public List<Process> passMe(User user) {
		List<Process> processes = new ArrayList<Process>();
		List<ProcessOperator> operators = getList(new ProcessOperator(null, user.getId()));
		for (ProcessOperator operator : operators) {
			Process process = get(new Process(null, operator.getProcessId()));
			if(process.getIsShow()) {
				List<ProcessOperator> list = getList(new ProcessOperator(process.getId(), null));
				process.setOperators(list);
				processes.add(process);
			}
		}
		return processes;
	}

	@Override
	public List<Process> iCreate(User user) {
		List<Process> processes = new ArrayList<Process>();
		List<FlowInstance> instances = getList(new FlowInstance(null, user.getId()));
		for(FlowInstance instance : instances) {
			Process process = new Process(instance.getId(), null);
			process.setIsShow(true);
			//TODO 后续性能优化
			List<Process> list = getList(process);
			process = list.get(list.size() - 1);
			List<ProcessOperator> operators = getList(new ProcessOperator(process.getId(), null));
			process.setOperators(operators);
			processes.add(process);
		}
		return processes;
	}

	@Override
	public List<Process> ccMe(User user) {
		List<Process> processes = new ArrayList<Process>();
		List<Link> links = new ArrayList<Link>();
		//TODO 可以通过传用户的群组ID或者岗位ID定位到相应的环节ID，后期考虑调整
		List<NotifyUser> users = getList(new NotifyUser(null, user.getId()));
		Long linkId = null;
		for (NotifyUser eu : users) {
			if(linkId != eu.getLinkId()) {
				links.add(new Link(linkId));
			}
			linkId = eu.getLinkId();
		}
		processes = getProcessesccMe(links);
		for(Process process : processes) {
			List<ProcessOperator> list = getList(new ProcessOperator(process.getId(), null));
			process.setOperators(list);
		}
		return processes;
	}

	@Override
	public List<Process> getProcesses(FlowInstance instance) {
		List<Process> processes = new ArrayList<Process>();
		List<FlowInstance> instances = new ArrayList<FlowInstance>();
		getInstances(instances, instance);
		for (FlowInstance fi : instances) {
			List<Process> list = getList(new Process(fi.getId(), null));
			for (Process process : list) {
				if(process.getIsShow()) {
					List<ProcessOperator> operators = getList(new ProcessOperator(process.getId(), null));
					process.setOperators(operators);
				}
			}
			processes.addAll(list);
		}
		return processes;
	}
	
	//递归获取流程实例和子流程
	private void getInstances(List<FlowInstance> instances, FlowInstance instance) {
		instances.add(instance);
		FlowInstance flowInstance = new FlowInstance();
		flowInstance.setFlowInstanceId(instance.getId());
		flowInstance = get(flowInstance);
		if(flowInstance != null) {
			getInstances(instances, flowInstance);
		}
	}
}
