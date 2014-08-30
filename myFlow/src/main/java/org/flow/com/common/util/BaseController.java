package util;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.ServletContextAware;

import cock.util.MyUtil;

import com.google.gson.Gson;

/**
 * spring下常用对象的初始获取,继承该类可以获得,同时可以获得向页面打印List<T>,List<Map<String,Ojbect>>,JSON
 * 等数据以供查验数据，方便调试
 * 
 * @author huangjp 2013-12-2
 */
public class BaseController implements ServletContextAware  {
	
	/**
	 * 全局对象，获取各种路径、存取全局数据
	 */
	protected static ServletContext servletContext; 
	
	/**
	 * 这个还有人不知道吗
	 */
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	/**
	 * 未测，慎用
	 */
	@Autowired
	protected ApplicationContext application;
	
	/**
	 * 用户会话，可存取用户信息和数据
	 */
	@Autowired
	protected HttpSession session;
	
	protected String getJson(Object object) {
		return new Gson().toJson(object);
	}
	
	/**
	 * 该对象提供许多与数据库联系的操作，如增、删、改、查等
	 */
//	@Autowired
//	protected ISystemService service;
//	@Autowired
//	protected IPublicService myService;
//	@Autowired
//	protected IMailService mailService;
	
	/**
	 * 该对象提供与数据库联系的操作，如增、删、改、查等,
	 * 有针对该类的ISystemService接口，不建议直接使用
	 */
//	@Autowired
//	protected IPublicDao publicDao;
//	@Autowired
//	protected IGenericDao dao;
	
	
	@Override
	public void setServletContext(ServletContext argContext) {
		servletContext = argContext;
	}
	
	@ModelAttribute
	public void setRequestAndResponse(HttpServletRequest request,HttpServletResponse response) {
		this.response = response;
		this.request = request;
	}
	
	public void writeJson(String string) {
		try {
			setHeadContentType("application/json");
			response.getWriter().print(string);
			closeOut();
		} catch (Exception e) {}
	}
	
	public <T> void writeList(List<T> list) {
		try {
			setHeadContentType("text/html;");
			response.getWriter().print("<table>");
			for (int i = 0; i < list.size(); i++) {
				if(i == 0) printTdFirst(MyUtil.castMap(list.get(i)));
				printTdLazy(MyUtil.castMap(list.get(i)));
			}
			response.getWriter().print("</table>");
			closeOut();
		} catch (Exception e) {System.out.println("BaseController.writeList()");}
	}
	
	public void writeMapList(List<Map<String, Object>> list) {
		try {
			setHeadContentType("text/html;");
			response.getWriter().print("<table>");
			for (int i = 0; i < list.size(); i++) {
				if(i == 0) printTdFirst(list.get(i));
				printTdLazy(list.get(i));
			}
			response.getWriter().print("</table>");
			closeOut();
		} catch (Exception e) {System.out.println("BaseController.writeMapList()");}
	}
	
	//打印table元素中第一行td
	private void printTdFirst(Map<String, Object> map) throws Exception {
		response.getWriter().print("<tr>");
		for (String key : map.keySet()) {
			response.getWriter().print("<td style='border:1px solid #ccc; padding:10px;' >");
			response.getWriter().print(key);
			response.getWriter().print("</td>");
		}
		response.getWriter().print("</tr>");
	}
	
	//打印table元素中余下的td
	private void printTdLazy(Map<String, Object> map) throws Exception {
		response.getWriter().print("<tr>");
		for (String key : map.keySet()) {
			response.getWriter().print("<td style='border:1px solid #ccc; padding:10px;'>" +
					"<div style='width:100px;height:20px; overflow: hidden;'>");
			response.getWriter().print(map.get(key));
			response.getWriter().print("</div></td>");
		}
		response.getWriter().print("</tr>");
	}
	
	//设置消息头、编码统一utf-8
	protected void setHeadContentType(String str) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {}
		response.setContentType(str+"charset=utf-8");
	}
	
	//关闭out
	private void closeOut() throws Exception {
		response.getWriter().flush();
		response.getWriter().close();
	}
	
}