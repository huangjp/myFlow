package com.flow.common.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.flow.common.dao.IPublicDao;
import com.flow.engine.dao.IFlowDao;
import com.flow.engine.entity.Link;
import com.flow.engine.entity.Process;

public abstract class DaoService {

	@Autowired
	private IPublicDao dao;
	
	@Autowired
	private IFlowDao flowDao;
	
	public <T> Long save(T entity) {
		Serializable id = dao.saveEntity(entity);
		return Long.parseLong(id.toString());
	}

	public <T> void update(T entity) {
		dao.updateEntityFieldsById(entity);
	}

	public <T> T get(T entity) {
		return dao.getEntityByAttrs(entity);
	}

	public <T> List<T> getList(T entity) {
		return dao.getEntitiesByAttr(entity);
	}
	
	public <T> List<T> getList(Class<T> c) {
		return dao.getEntities(c);
	}
	
	public <T> void delete(T entity) {
		dao.deleteEntity(entity);
	}

	public List<Process> getProcessesMyToDo(List<Link> links) {
		return flowDao.getProcessesMyToDo(links);
	}
	
	public List<Process> getProcessesccMe(List<Link> links) {
		return flowDao.getProcessesMyToDo(links);
	}
}
