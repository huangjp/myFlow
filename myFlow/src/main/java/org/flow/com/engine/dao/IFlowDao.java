package dao;

import java.util.List;

import entity.FlowInstance;
import entity.Link;
import entity.Process;

public interface IFlowDao {
	
	List<Process> getProcessesMyToDo(List<Link> links);
	
	List<Process> getProcessesccMe(List<Link> links);
	
	List<Process> getICreate(List<FlowInstance> instances);
	
//	<T> Serializable save(T entity);
	
//	<T> void update(T entity);
	
//	<T> void Delete(T entity);
	
//	<T> List<T> getList(T entity);
	
//	<T> T get(T entity);
	
//	<T> void updateSelective(T entity);
}
