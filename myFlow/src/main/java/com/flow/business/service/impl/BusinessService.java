package com.flow.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flow.business.entity.BsiGroup;
import com.flow.business.entity.BsiGroupHasPost;
import com.flow.business.entity.BsiPost;
import com.flow.business.entity.BsiUser;
import com.flow.business.entity.BsiUserHasGroup;
import com.flow.business.entity.BsiUserHasPost;
import com.flow.business.entity.Menu;
import com.flow.business.entity.MyBusiness;
import com.flow.business.entity.vo.BusinessDataVo;
import com.flow.business.service.IBusinessService;
import com.flow.common.base.DaoService;
import com.flow.common.util.RecursiveUtil;
import com.flow.engine.entity.Business;
import com.flow.engine.entity.Flow;
import com.flow.engine.entity.User;
import com.flow.engine.service.IFlowInstanceService;

@Service
public class BusinessService extends DaoService implements IBusinessService {

	@Resource
	private IFlowInstanceService instanceService;
	
	@Override
	public BusinessDataVo getBusinesses() {
		try {
			BusinessDataVo vos = new BusinessDataVo();
			List<BsiGroup> groups = getList(BsiGroup.class);
			List<BsiUser> users = getList(BsiUser.class);
			List<BsiPost> posts = getList(BsiPost.class);
			List<BsiUserHasGroup> uhgs = getList(BsiUserHasGroup.class);
			List<BsiUserHasPost> uhps = getList(BsiUserHasPost.class);
			List<BsiGroupHasPost> ghps = getList(BsiGroupHasPost.class);
			setUserToGroup(users, groups, uhgs);
			setUserToPost(users, posts, uhps);
			setPostToGroup(posts, groups, ghps);
			setSonGroups(groups);
			vos.setGroups(groups);
			return vos;
		} catch (Exception e) {
			throw new RuntimeException("Collection of query entity fails, the transaction should be rolled back");
		}
	}
	
	private void setPostToGroup(List<BsiPost> posts, List<BsiGroup> groups, List<BsiGroupHasPost> ghps) {
		for(BsiGroup group : groups) {
			List<BsiPost> sonPosts = new ArrayList<BsiPost>();
			for(BsiPost post : posts) {
				for (BsiGroupHasPost ghp : ghps) {
					if(post.getId() == ghp.getPostId() && group.getId() == ghp.getGroupId()) {
						sonPosts.add(post);
					}
				}		
			}
			group.setPosts(sonPosts);
		}
	}
	
	private void setUserToGroup(List<BsiUser> users, List<BsiGroup> groups, List<BsiUserHasGroup> uhgs) {
		for(BsiGroup group : groups) {
			List<BsiUser> sonUsers = new ArrayList<BsiUser>();
			for(BsiUser user : users) {
				for (BsiUserHasGroup uhg : uhgs) {
					if(user.getId() == uhg.getUserId() && group.getId() == uhg.getGroupId()) {
						sonUsers.add(user);
					}
				}		
			}
			group.setUsers(sonUsers);
		}
	}
	
	@Override
	public List<BsiGroup> getGroupHasUser() {
		List<BsiGroup> groups = getList(BsiGroup.class);
		List<BsiUser> users = getList(BsiUser.class);
		List<BsiUserHasGroup> uhgs = getList(BsiUserHasGroup.class);
		setUserToGroup(users, groups, uhgs);
		return groups;
	}

	@Override
	public List<BsiPost> getPostHasUser() {
		List<BsiPost> posts = getList(BsiPost.class);
		List<BsiUser> users = getList(BsiUser.class);
		List<BsiUserHasPost> uhps = getList(BsiUserHasPost.class);
		setUserToPost(users, posts, uhps);
		return posts;
	}

	@Override
	public List<BsiUser> getUsers() {
		List<BsiUser> users = getList(BsiUser.class);
		return users;
	}

	private void setUserToPost(List<BsiUser> users, List<BsiPost> posts, List<BsiUserHasPost> uhps) {
		for(BsiPost post : posts) {
			List<BsiUser> sonUsers = new ArrayList<BsiUser>();
			for(BsiUser user : users) {
				for (BsiUserHasPost uhp : uhps) {
					if(user.getId() == uhp.getUserId() && post.getId() == uhp.getPostId()) {
						sonUsers.add(user);
					}
				}		
			}
			post.setUsers(sonUsers);
		}
	}
	
	private void setSonGroups(List<BsiGroup> AllGroup) {
		for (BsiGroup bsiGroup : AllGroup) {
			List<BsiGroup> sonGroup = new ArrayList<BsiGroup>();
			for(BsiGroup group : AllGroup) {
				if(bsiGroup.getId() == group.getParentId()) {
					sonGroup.add(group);
				}
			}
			bsiGroup.setGroups(sonGroup);
		}
		for(int i = AllGroup.size() - 1; i >= 0; i--) {
			if(AllGroup.get(i).getParentId() != null && AllGroup.get(i).getParentId() != 0) {
				AllGroup.remove(i);
			}
		}
	}

	@Override
	public Map<Class<?>, Long> saveBusiness(BsiGroup group, BsiUser user, BsiPost post) {
		if(group == null || user == null || post == null) {
			throw new RuntimeException("save the entity cannot be empty");
		}
		
		try {
			Long groupId = save(group);
			Long userId = save(user);
			Long postId = save(post);
			Map<Class<?>, Long> map = new HashMap<Class<?>, Long>();
			map.put(BsiGroup.class, groupId);
			map.put(BsiUser.class, userId);
			map.put(BsiPost.class, postId);
			return map;
		} catch (Exception e) {
			throw new RuntimeException("entity save failed, transaction should be rolled back");
		}
	}

	@Override
	public <T> boolean deleteBusiness(T entity) {

		if(entity == null) {
			throw new RuntimeException("entity or entity ID can not empty");
		}
		
		try {
			delete(entity);
		} catch (Exception e) {
			throw new RuntimeException("entity to remove failure, transaction should be rolled back");
		}
		
		return true;
	}

	@Override
	public <T> boolean updateBusiness(T entity) {
		if(entity == null) {
			throw new RuntimeException("entity or link ID can not empty");
		}
		
		try {
			update(entity);
		} catch (Exception e) {
			throw new RuntimeException("entity change failure, transaction should be rolled back");
		}
		
		return true;
	}

	@Override
	public Long saveBusiness(MyBusiness business) {
		if(business == null) {
			throw new RuntimeException("entity or link ID can not empty");
		}
		
		try {
			Long id = save(business);
			business.setId(id);
			//开户流程
			instanceService.startFlowInstance(new Flow(business.getFlowId()), new Business(id.toString()), new User(business.getUserId()));
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			delete(business);
			throw new RuntimeException("entity change failure, transaction should be rolled back");
		}
	}

	@Override
	public <T> T getT(T entity) {
		try {
			if(null == entity) {
				return null;
			} else {
				return super.get(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException("Query entity fails, the transaction should be rolled back");
		}
	}

	@Override
	public List<Menu> getMenus(String type) {
		List<Menu> menus = new ArrayList<Menu>();
		try {
			if("all".equals(type)) {
				menus = RecursiveUtil.recursive(this.getList(Menu.class), "id", "menuId", "menus");
			} else if("parent".equals(type)) {
				Menu menu = new Menu();
				menu.setMenuId(null);
				menus = this.getList(menu);
			} else {
				Menu menu = new Menu();
				menu.setMenuId(Integer.parseInt(type));
				menus = this.getList(menu);
			} 
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menus;
	}
}
