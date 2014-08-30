package com.flow.engine.service;

import java.util.List;

import com.flow.engine.entity.Group;
import com.flow.engine.entity.Post;
import com.flow.engine.entity.User;

/**
 * 设置基础数据，如使用流程引擎时需要的，用户数据，群组数据，岗位数据等
 * @author Administrator
 *
 */
public interface ISetFlowBaseDataByHibernate {
	
	/**
	 * 设置岗位
	 * @return
	 */
	<T> List<Post> setPostData(Class<T> c);
	
	/**
	 * 设置群组
	 * @return
	 */
	<T> List<Group> setGroupData(Class<T> c);
	
	/**
	 * 设置用户
	 * @return
	 */
	<T> List<User> setUserData(Class<T> c);
}
