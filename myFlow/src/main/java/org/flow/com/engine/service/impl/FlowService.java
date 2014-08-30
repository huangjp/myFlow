package service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import service.IFlowService;
import base.DaoService;
import entity.Business;
import entity.Flow;
import entity.FlowConnector;
import entity.FlowInstance;
import entity.Process;
import entity.User;
import entity.vo.FlowBusinessVo;

@Service
public class FlowService extends DaoService implements IFlowService { 

	@Override
	public Long saveFlow(Flow flow, List<FlowConnector> uses) {
		try {
			Long id = save(flow);
			if(uses != null) {
				for (FlowConnector use : uses) {
					use.setFlowId(id);
					save(use);
				}
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Process save failed, the transaction should be rolled back");
		}
	}

	@Override
	public boolean deleteFlow(Flow flow) {
		try {
			delete(flow);
		} catch (Exception e) {
			throw new RuntimeException("Process to remove failure, transaction should be rolled back");
		}
		return true;
	}

	@Override
	public boolean updateFlow(Flow flow) {
		try {
			update(flow);
		} catch (Exception e) {
			throw new RuntimeException("Process change failure, transaction should be rolled back");
		}
		return true;
	}

	@Override
	public Flow getFlow(Flow flow) {
		try {
			if(null == flow) {
				return null;
			} else {
				return get(flow);
			}
		} catch (Exception e) {
			throw new RuntimeException("Query process fails, the transaction should be rolled back");
		}
	}

	@Override
	public List<Flow> getFlows(Flow flow) {
		try {
			if(null == flow) {
				return getList(Flow.class);
			} else {
				return getList(flow);
			}
		} catch (Exception e) {
			throw new RuntimeException("Collection of query process fails, the transaction should be rolled back");
		}
	}

	@Override
	public FlowBusinessVo getFlowBusinessVo(Process process) {
		FlowBusinessVo vo = new FlowBusinessVo();
		FlowInstance flow = get(new FlowInstance(process.getFlowInstanceId(), null));
		List<Process> processes = getList(new Process(process.getFlowInstanceId(), null));
		Business business = get(new Business(processes.get(0).getBusinessId()));
		User user = new User(flow.getCretaeUserId());
		vo.setBusiness(business);
		vo.setFlow(flow);
		vo.setProcesses(processes);
		vo.setUser(user);
		return vo;
	}

}