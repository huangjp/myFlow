package entity.vo;

import java.util.List;

import entity.Business;
import entity.FlowInstance;
import entity.Process;
import entity.User;

public class FlowBusinessVo {
	
	private FlowInstance flowInstance;
	private Business business;
	private User user;
	private List<Process> processes;

	public List<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}

	public FlowInstance getFlow() {
		return flowInstance;
	}

	public void setFlow(FlowInstance flow) {
		this.flowInstance = flow;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
