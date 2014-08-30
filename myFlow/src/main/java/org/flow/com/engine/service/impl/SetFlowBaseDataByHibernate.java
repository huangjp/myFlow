package service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.springframework.stereotype.Service;

import service.ISetFlowBaseDataByHibernate;
import base.DaoService;
import cock.util.MyUtil;
import entity.Group;
import entity.Post;
import entity.User;

@Service
public class SetFlowBaseDataByHibernate extends DaoService implements ISetFlowBaseDataByHibernate {

	@Override
	public <T> List<Post> setPostData(Class<T> c) {
		List<Post> posts = new ArrayList<Post>();
		List<T> list = getList(c);
		for (T t : list) {
			Object o = getFields(t.getClass());
			Post post = new Post();
			post.setId(Long.parseLong(o.toString()));
			Long id = save(post);
			post.setId(id);
			posts.add(post);
		}
		return posts;
	}

	@Override
	public <T> List<Group> setGroupData(Class<T> c) {
		List<Group> groups = new ArrayList<Group>();
		List<T> list = getList(c);
		for (T t : list) {
			Object o = getFields(t.getClass());
			Group group = new Group();
			group.setId(Long.parseLong(o.toString()));
			Long id = save(group);
			group.setId(id);
			groups.add(group);
		}
		return groups;
	}

	@Override
	public <T> List<User> setUserData(Class<T> c) {
		List<User> users = new ArrayList<User>();
		List<T> list = getList(c);
		for (T t : list) {
			Object o = getFields(t.getClass());
			User user = new User();
			user.setId(Long.parseLong(o.toString()));
			Long id = save(user);
			user.setId(id);
			users.add(user);
		}
		return users;
	}

	private <T> Object getFields(Class<T> c) {
		List<Field> fields = new ArrayList<Field>();
		Object o = null;
		Field[] attrs = c.getDeclaredFields();
		for (Field f : attrs) {
			Id pri = f.getAnnotation(Id.class);
			if(pri != null) {
				fields.add(f);
				try {
					Method m = c.getDeclaredMethod("get" + MyUtil.initcap(f.getName()));
					if(m != null) o = m.invoke(c);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} 
		}
		if(fields.size() != 1) 
			throw new RuntimeException("Does not support the business more than one primary key in the table");
		if (o != null) {
			return o;
		} else {
			throw new RuntimeException("Objects have no value");
		}
	}
	
}
