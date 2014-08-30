package com.flow.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import util.MyUtil;

import com.flow.common.dao.IPublicDao;

@SuppressWarnings({ "unchecked" })
public class PublicDao implements IPublicDao {
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private JdbcTemplate jdbcTamplate;
	
	public PublicDao() {}
	
	protected Query myQuery(String sql) {
		Query query = mySession().createQuery(sql);
		return query;
	}
	
	protected Session mySession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public <T> T getInstance(Class<T> c){
		try {
			return c.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public <T> void deleteEntity(T entity){
		Transaction tr = mySession().beginTransaction();
		mySession().delete(entity);
		tr.commit();
		mySession().flush();
	}

	@Override
	@Deprecated
	public <T extends Collection<T>> void deleteByCollection(List<T> entities) {
		//TODO 没有hibernateTemplate了，得重写
//		hibernateTemplate.deleteAll(entities);
	}

	@Override
	public <T> void deleteEntityFieldsById(Class<T> c, Map<String, Object> map) {
		String name = c.getSimpleName().toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("delete from " + name + " where ");
		int count = 0;
		for (String key : map.keySet()) {
			count++;
			if(count == 1) {
				sb.append(key + " = " + getString(map.get(key)));
			}else {
				sb.append(" and " + key + " = " + getString(map.get(key)));
			}
		}
		jdbcTamplate.execute(sb.toString());
	}

	@Override
	public <T> void deleteEntityFieldsById(T entity) {
		try {
			Map<String, Object> map = castMap(entity);
			deleteEntityFieldsById(entity.getClass(), map);
		} catch (Exception e) {}
	}

	@Override
	public <T> T getEntityById(Class<T> c, int id){
		try {
			if(id == 0) return c.newInstance();
		} catch (Exception e) {}
		List<T> list = (List<T>) myQuery("from "+c.getSimpleName()+" where id="+id).list();
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public <T> void updateEntity(T entity) {
		mySession().update(entity);
	}

	@Override
	@Deprecated
	public <T> void updateEntityById(Class<T> c, int id, String field,
			Object fieldvalue) {
//		String name = c.getSimpleName().toLowerCase();
//		jdbcTamplate.execute(SqlConstant.updateByIdToField(name, id, field,
//				fieldvalue));
	}

	@Override
	public <T> Serializable saveEntity(T entity) {
		Serializable id = mySession().save(entity); 
		return id;
	}

	@Override
	public <T> void saveOrUpdateEntity(T entity) {
		mySession().saveOrUpdate(entity);
	}

	@Override
	public <T> List<T> getEntities(Class<T> c) {
		List<T> list = mySession().createCriteria(c).list();
		return list;
	}

	@Override
	public <T> List<Map<String, Object>> getEntityContent(Class<T> c,
			Map<String, Object> map) {
		if(map == null) return new ArrayList<Map<String,Object>>();
		String name = c.getSimpleName().toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("select " + map.get("fields") + " from " + name + " where ");
		for (String key : map.keySet()) {
			String str = (String) map.get(key);
			if (!("fields".equals(key))) {
				String[] strs = str.split("-");
				sb.append("" + key + " = " + strs[0]);
				for (int i = 1; i < strs.length; i++) {
					sb.append(" or " + key + " = " + strs[i]);
				}
			}
		}
		List<Map<String, Object>> list = (List<Map<String, Object>>) 
				jdbcTamplate.queryForList(sb.toString()); 
		return list == null?new ArrayList<Map<String,Object>>():list;
	}

	@Override
	public <T> List<T> getEntitiesByAttr(Class<T> c, String hql) {
		return myQuery(hql).list();
	}
	
	@Override
	public <T> List<T> getEntitiesByAttrAnd(Class<T> c, Map<String, Object> map) {
		if(map == null || map.isEmpty()) {
			List<T> list = mySession().createCriteria(c).list();
			return list;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("from " + c.getSimpleName() + " where ");
		int count = 0;
		for (String key : map.keySet()) {
			count++;
			if (count == 1) {
				sb.append("" + key + " = " + map.get(key));
			} else {
				sb.append(" and " + key + " = " + map.get(key));
			}
		}
		List<T> list = (List<T>) myQuery(sb.toString()).list();
		return list;
	}
	
	@Override
	@Deprecated
	public <T> List<T> getEntitiesByAttrOr(Class<T> c, Map<String, Object> map) {
		if(map == null) return new ArrayList<T>();
		StringBuilder sb = new StringBuilder();
		sb.append("from " + c.getSimpleName() + " where ");
		int count = 0;
		String[] paramNames = new String[map.size()];
		Object[] values = new Object[map.size()];
		for (String key : map.keySet()) {
			paramNames[count] = key;
			values[count] = map.get(key);
			count++;
			if (count == 1) {
				sb.append("" + key + " = :" + key);
			} else {
				sb.append(" or " + key + " = :" + key);
			}
		}
//		List<T> list = (List<T>) .findByNamedParam(sb.toString(), paramNames, values);
//		List<T> list = (List<T>) hibernateTemplate.find(sb.toString());
		return null; //list TODO 
	}

	@Override
	public <T> List<T> getEntitiesByAttrAnd(Class<T> c, String field, List<Object> list) {
		if(list == null || list.isEmpty()) return new ArrayList<T>();
		StringBuilder sb = new StringBuilder();
		sb.append("from " + c.getSimpleName() + " where ");
		int count = 0;
		for (Object key : list) {
			count++;
			if (count == 1) {
				sb.append("" + field + " = " + key);
			} else {
				sb.append(" and " + field + " = " + key);
			}
		}
		List<T> result = (List<T>) myQuery(sb.toString()).list();
		return result;
	}

	@Override
	public <T> List<T> getEntitiesByAttrOr(Class<T> c, String field, List<Object> list) {
		if(list == null || list.isEmpty()) return new ArrayList<T>();
		StringBuilder sb = new StringBuilder();
		sb.append("from " + c.getSimpleName() + " where ");
		int count = 0;
		for (Object key : list) {
			count++;
			if (count == 1) {
				sb.append("" + field + " = " + key);
			} else {
				sb.append(" or " + field + " = " + key);
			}
		}
		List<T> result = (List<T>) myQuery(sb.toString()).list();
		return result;
	}

	@Override
	public <T> List<T> getEntitiesById(Class<T> c, int id) {
		String sql = "from "+c.getSimpleName()+" where id="+id;
		List<T> list = (List<T>) myQuery(sql).list();
		return list;
	}

	@Override
	public <T> List<T> getEntitiesById(T entity) {
		if(entity == null) new ArrayList<T>();
		try {
			Method m = entity.getClass().getMethod("getId");
			int id = (Integer) m.invoke(entity);
			String sql = "from "+entity.getClass().getSimpleName()+" where id="+id;
			List<T> list = (List<T>) myQuery(sql).list();
			return list;
		} catch (Exception e) {
			return new ArrayList<T>();
		}
	}
	
	private <T> Map<String, Object> castMap(T entity) 
			throws IllegalArgumentException, IllegalAccessException, 
			InvocationTargetException, SecurityException, NoSuchMethodException {
		return SSHUtil.castMap(entity);
	}

	@Override
	public <T> void updateEntityFieldsById(T entity) {
		try {
			Map<String, Object> map = castMap(entity);
			updateEntityFieldsById(entity.getClass(), map);
		} catch (Exception e) { e.printStackTrace();}
	}
	
	private Object getString(Object str) {
		return MyUtil.getString(str);
	}

	@Override
	public <T> void updateEntityFieldsById(Class<T> c, Map<String, Object> map) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("update " + getTableName(c) + " set ");
			int count = 0;
			for (String key : map.keySet()) {
				if ("ID".equalsIgnoreCase(key)) continue;
				count++;
				if(count == 1) {
					sb.append("" + key + " = " + map.get(key));
				} else {
					sb.append("," + key + " = " + map.get(key));
				}
			}
			sb.append(" where id = " + (map.get("ID") == null ? map.get("id") : map.get("ID")));
			jdbcTamplate.execute(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PublicDao.updateEntityFieldsById()异常");
		}
	}

	@Override
	public <T> T getEntityByAttrs(Class<T> c, Map<String, Object> map) {
		try {
			if(map == null) return c.newInstance();
		} catch (Exception e) {} 
		StringBuilder sb = new StringBuilder();
		sb.append("from "+c.getSimpleName()+" where ");
		int count = 0;
		for (String key : map.keySet()) {
			count++;
			if (count == 1) {
				sb.append("" + key + " = " + map.get(key));
			} else {
				sb.append(" and " + key + " = " + map.get(key));
			}
		}
		List<T> list = (List<T>) myQuery(sb.toString()).list();
		return list.isEmpty()?null:list.get(0);
	}

	@Override
	public <T> void updateEntitiesById(Class<T> c,List<Map<String, Object>> list) {
		String name = c.getSimpleName().toLowerCase();
		for (int i = 0; i < list.size(); i++) {
			StringBuilder sb = new StringBuilder();
			Map<String, Object> map = list.get(i);
			sb.append("update " + name + " set ");
			int count = 0;
			for (String key : map.keySet()) {
				count++;
				if (count == 1) {
					sb.append("" + key + " = " + map.get(key));
				} else {
					sb.append("," + key + " = " + map.get(key));
				}
			}
			sb.append(" where id = " + map.get("id"));
			jdbcTamplate.execute(sb.toString());
		}
	}

	@Override
	public <T> List<Serializable> saveEntities(List<T> list) {
		List<Serializable> ser = new ArrayList<Serializable>();
		for (int i = 0; i < list.size(); i++) {
			Serializable s = mySession().save(list.get(i));
			if(Integer.parseInt(s.toString()) > 0) ser.add(s);
		}
		return ser;
	}

	@Override
	public <T> T getEntityBySerializable(Class<T> c, Serializable id) {
		try {
			if(id == null) return c.newInstance();
		} catch (Exception e) {}
		return (T) this.mySession().get(c, id);
	}

	@Override
	@Transactional
	public <T> List<T> getEntityForPage(Class<T> c, int rows, int page) {
		Query query = myQuery("from " + c.getSimpleName().toLowerCase());
		query.setFirstResult((page - 1) * rows);
		query.setMaxResults(rows);
		return query.list();
	}

//	@Override
//	public <T> List<T> getEntitiesForPage(final String hql,
//			final PageModel pageModel) {
//		List<T> list = this.hibernateTemplate.executeFind(new HibernateCallback<Object>() {
//			public Object doInHibernate(Session session)
//					throws HibernateException, SQLException {
//				Query query = session.createQuery(hql);
//				Integer pageNow = pageModel.getPageNow() - 1;
//				query.setFirstResult(pageNow * pageModel.getPageSize());
//				query.setMaxResults(pageModel.getPageSize());
//				List<T> list = query.list();
//				return list;
//			}
//		});
//		return list;
//	}

	@Override
	public <T> int getEntityRows(Class<T> c) {
		
		return 0;
	}

	@Override
	public <T> StringBuilder getEntityTree(Class<T> c, Map<String, Object> map) {
		if(map == null) return new StringBuilder();
		String name = c.getSimpleName().toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("select " + map.get("fields") + " from " + name + " where ");
		for (String key : map.keySet()) {
			String str = (String) map.get(key);
			if (!("fields".equals(key))) {
				String[] strs = str.split("-");
				sb.append("" + key + " = " + strs[0]);
				for (int i = 1; i < strs.length; i++) {
					sb.append(" or " + key + " = " + strs[i]);
				}
			}
		}
		return sb;
	}

	@Override
	public <T> T updateAndGetEntityById(T entity) {
		updateEntityFieldsById(entity);
		try {
			Method method = entity.getClass().getMethod("getId");
			Integer id = (Integer) method.invoke(entity);
			return (T) getEntityById(entity.getClass(), id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public <T> List<T> getEntitiesByAttr(T entity) {
		if(entity == null) return new ArrayList<T>();
		try {
			return (List<T>)getEntitiesByAttrAnd(
					entity.getClass(), castMap(entity));
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}
	}

	@Override
	public <T> T getEntityByAttrs(T entity) {
		try {
			Map<String, Object> map = castMap(entity);
			return (T) getEntityByAttrs(entity.getClass(), map);
		} catch (Exception e) {
			e.printStackTrace();
			return entity;
		}
	}

	@Override
	public <T> List<Map<String, Object>> getEntitiesByJDBCAttr(String sql) {
		return jdbcTamplate.queryForList(sql);
	}
	
	private <T> String getClassAttrs(Class<T> c) {
		return MySpringUtil.getClassAttrs(c);
	}
	
	private <T> String getTableName(Class<T> c) {
		return MySpringUtil.getTableName(c);
	}
	
	private <T> T castEntity(Class<T> c, Map<String, Object> map) throws Exception {
		return MyUtil.castEntity(c, map);
	}

	@Override
	public <T> List<T> getEntitiesByAttrLike(Class<T> c, Map<String, Object> map) {
		if(map == null) return new ArrayList<T>();
		StringBuilder sb = new StringBuilder();
		String names = getClassAttrs(c);
		sb.append("select " + names + " from " + c.getSimpleName().toLowerCase() + " where ");
		int count = 0;
		for (String key : map.keySet()) {
			count++;
			if (count == 1) {
				sb.append("" + key + " like '%" + map.get(key) + "%'");
			} else {
				sb.append(" and " + key + " like '%" + map.get(key) + "%'");
			}
		}
		List<Map<String, Object>> mList = (List<Map<String,Object>>)jdbcTamplate.queryForList(sb.toString());
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < mList.size(); i++) {
			try {
				list.add(castEntity(c, mList.get(i)));
			} catch (Exception e) {System.out.println("异常了");}
		}
		return list;
	}

	@Override
	public <T> List<T> getEntitiesByAttrLike(T entity) {
		try {
			return (List<T>) getEntitiesByAttrLike(
					entity.getClass(), castMap(entity));
		} catch (Exception e) { return new ArrayList<T>();}
	}

	@Override
	public <T> Integer getEntityMaxId(Class<T> c) {
		String sql = "select max(t.id) from " + c.getSimpleName().toLowerCase() + " as t";
		return jdbcTamplate.queryForInt(sql);
	}

//	@Override
//	public <T> List<Map<String,Object>> getEntitiesByJDBCAttr(Class<T> c, final String sql) {
//		try {
//	
//		System.out.println("ddddddd");
//			
////			return this.hibernateTemplate.executeFind(new HibernateCallback(){
////
////				@Override
////				public Object doInHibernate(Session arg0)
////						throws HibernateException, SQLException {
////					return arg0.createSQLQuery(sql);
////				}
////				
////			});
//			
//			return  jdbcTamplate.queryForList(sql);
//		} catch (Exception e) {
//			return null;
//		}
//	}

}

