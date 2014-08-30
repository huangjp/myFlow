package com.flow.engine.service;

import java.util.List;

import com.flow.engine.entity.Condition;

public interface IConditionService {

	Long saveCondition(Condition condition);
	
	boolean deleteCondition(Condition condition);
	
	boolean updateCondition(Condition condition);
	
	Condition getCondition(Condition condition);
	
	List<Condition> getConditions(Condition condition);
	
}
