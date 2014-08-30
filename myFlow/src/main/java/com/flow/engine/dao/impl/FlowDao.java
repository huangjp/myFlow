package com.flow.engine.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.flow.common.dao.impl.PublicDao;
import com.flow.engine.dao.IFlowDao;
import com.flow.engine.entity.FlowInstance;
import com.flow.engine.entity.Link;
import com.flow.engine.entity.Process;

@SuppressWarnings("unchecked")
@Repository
public class FlowDao extends PublicDao implements IFlowDao {

	@Override
	public List<Process> getProcessesMyToDo(List<Link> links) {
		List<Process> processes = new ArrayList<Process>();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < links.size(); i++) {
			if(i == 0) {
				sb.append(links.get(i).getId());
			} else {
				sb.append(",");
				sb.append(links.get(i).getId());
			}
		}
		String str = "";
		if(sb.length() != 0) {
			str = sb.toString();
		} else {
			str = "''";
		}
		String sql = "from "+Process.class.getSimpleName()+" where is_show = false and current_link_id in(" + str + ")";
		processes = myQuery(sql).list();
		return processes;
	}

	@Override
	public List<Process> getICreate(List<FlowInstance> instances) {
		List<Process> processes = new ArrayList<Process>();
		for(FlowInstance instance : instances) {
			//TODO 待测
			String sql = "from "+Process.class.getSimpleName()+" where is_show = true and flow_instance_id = " + instance.getId();
			processes.addAll(myQuery(sql).list());
		}
		return processes;
	}

	@Override
	public List<Process> getProcessesccMe(List<Link> links) {
		List<Process> processes = new ArrayList<Process>();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < links.size(); i++) {
			if(i == 0) {
				sb.append(links.get(i).getId());
			} else {
				sb.append(",");
				sb.append(links.get(i).getId());
			}
		}
		String str = "";
		if(sb.length() != 0) {
			str = sb.toString();
		} else {
			str = "''";
		}
		String sql = "from "+Process.class.getSimpleName()+" where is_show = true and current_link_id in(" + str + ")";
		processes = myQuery(sql).list();
		return processes;
	}

}
