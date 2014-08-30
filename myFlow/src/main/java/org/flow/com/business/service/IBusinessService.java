package service;

import java.util.List;
import java.util.Map;

import entity.BsiGroup;
import entity.BsiPost;
import entity.BsiUser;
import entity.MyBusiness;
import entity.vo.BusinessDataVo;

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
