package service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import service.IConditionService;
import base.DaoService;
import entity.Condition;

@Service
public class ConditionService extends DaoService implements IConditionService {
	
	@Override
	public Long saveCondition(Condition condition) {
		try {
			Long id = save(condition);
			return id;
		} catch (Exception e) {
			throw new RuntimeException("Save failed, the transaction should be rolled back");
		}
	}

	@Override
	public boolean deleteCondition(Condition condition) {
		try {
			delete(condition);
		} catch (Exception e) {
			throw new RuntimeException("Conditions to delete failure, transaction should be rolled back");
		}
		return true;
	}

	@Override
	public boolean updateCondition(Condition condition) {
		try {
			update(condition);
		} catch (Exception e) {
			throw new RuntimeException("Conditions change fails, the transaction should be rolled back");
		}
		return true;
	}

	@Override
	public Condition getCondition(Condition condition) {
		try {
			if(null == condition) {
				return null;
			} else {
				return get(condition);
			}
		} catch (Exception e) {
			throw new RuntimeException("Query condition fails, the transaction should be rolled back");
		}
	}

	@Override
	public List<Condition> getConditions(Condition condition) {
		try {
			if(null == condition) {
				return getList(Condition.class);
			} else {
				return getList(condition);
			}
		} catch (Exception e) {
			throw new RuntimeException("Query conditions set fails, the transaction should be rolled back");
		}
	}

}
