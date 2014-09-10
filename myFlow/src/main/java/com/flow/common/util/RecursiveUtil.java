package com.flow.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cock.util.MyUtil;

/**
 * 递归工具//TODO 还有针对MAP的后期再加
 * @author huangjp 2014-9-5
 */
public class RecursiveUtil {
	
	static class Recursive<T> {
		
		List<T> newList;

		public Recursive() {
			super();
			newList = new ArrayList<T>();
		}
		
		@SuppressWarnings({"unchecked" })
		public List<T> run(List<T> list, String only, String parent, String subList, List<T> newList) 
				throws NoSuchMethodException, SecurityException, IllegalAccessException, 
				IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
			if(null == newList) newList = this.newList;
			if(newList.isEmpty()) {//先找第一批父亲
				for(T t : list) {
					if("".equals(get(t, parent)) || "0".equals(get(t, parent))) {
						newList.add(t);//表示父节点
						set(t, parent, null);
					}
				}
			}
			for(T o : newList) {// 为第一批父亲找孩子
				List<T> sub = new ArrayList<T>();
				for(T t : list) {
					String s = get(t, parent);
					if(!"".equals(s) && get(o, only).equals(s)) {//表示有对应关系，需要取出来
						sub.add(t);
						set(t, parent, null);
					}
				}
				set(o, subList, sub);// 将对象放入
			}
			boolean b = true;//确定是否取完
			for(T t : list) {
				if(!"".equals(get(t, parent))){//表示其被取过，不能再取
					b = false;
				} 
			}
			if(b) {//找完则直接retrun
				return newList;
			}
			for(T t : newList) {
				Object o = get(t, subList);
				if(o instanceof List) {
					List<T> ts = (List<T>) o;
					if(!ts.isEmpty()) {
						run(list, only, parent, subList, ts);
					}
				}
			}
			return newList;
		}
		
		private String get(T t,String field) throws NoSuchMethodException, SecurityException, 
				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			String name = MyUtil.firstToUpperCase(field);
			Method m = t.getClass().getDeclaredMethod("get" + name);
			Object o = m.invoke(t);
			String s = null == o ? "" : o.toString();
			return s;
		}
		
		private void set(T t,String field,Object o) throws NoSuchMethodException, SecurityException, 
				IllegalAccessException, IllegalArgumentException, 
				InvocationTargetException, NoSuchFieldException {
			String name = MyUtil.firstToUpperCase(field);
			Field f = t.getClass().getDeclaredField(field);
			Method m = t.getClass().getDeclaredMethod("set" + name, f.getType());
			// TODO 此处需要判断类型，其为基本类型时不能使用Object类型的参数，要么抛出异常，要么转换为其各自的类型进行判断
			m.invoke(t, o);
		}
	}
	
	/**
	 * 为集合递归树结果
	 * @param list 对象集合
	 * @param only 表示主键属性名
	 * @param parent 表示父主键属性名
	 * @param subList 表示子集合属性名
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException 
	 */
	public static <T> List<T> recursive(List<T> list, String only, String parent, String subList) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		if(null == list || list.isEmpty()) {
			return list;
		}
		Recursive<T> re = new Recursive<T>();
		List<T> newList = re.run(list, only, parent, subList, null);
		return newList;
	}
}
