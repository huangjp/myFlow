package com.flow.business.service;

import java.util.List;
import java.util.Map;

import com.flow.business.entity.BsiGroup;
import com.flow.business.entity.BsiPost;
import com.flow.business.entity.BsiUser;
import com.flow.business.entity.MyBusiness;
import com.flow.business.entity.vo.BusinessDataVo;

public interface IBusinessService {

	BusinessDataVo getBusinesses();
	
	Map<Class<?>, Long> saveBusiness(BsiGroup group, BsiUser user, BsiPost post);
	
	Long saveBusiness(MyBusiness business);
	
	<T> boolean deleteBusiness(T entity);
	
	<T> boolean updateBusiness(T entity);
	
	<T> T getT(T entity);
	
	List<BsiGroup> getGroupHasUser();
	
	List<BsiPost> getPostHasUser();
	
	List<BsiUser> getUsers();
}
