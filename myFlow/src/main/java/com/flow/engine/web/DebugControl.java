package com.flow.engine.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import util.MyUtil;
import util.debug.DebugUtil;

import com.flow.business.web.BusinessControl;
import com.flow.common.util.BaseController;

/** 
 * @ClassName: DebugControl 
 * @Description: (调试用) 
 * @author huangjp
 * @date 2014年4月1日 下午3:03:58 
 */
@Controller
public class DebugControl extends BaseController {
	
	/**
	 * 获取请求路径，实体类属性注释
	 * @param model
	 * @return
	 */
	@RequestMapping("/debug.do")
	public String debug(Model model) {
		model.addAttribute("flow", DebugUtil.getRequestMapping(FlowControl.class, this.request));
		model.addAttribute("business", DebugUtil.getRequestMapping(BusinessControl.class, this.request));
		model.addAttribute("debug", DebugUtil.getRequestMapping(DebugControl.class, this.request));
		model.addAttribute("entity", getEntities());
		return "debug";
	}
	
	/**
	 * 测试增删改查等基本业务，传入实体类的名字
	 * @param model
	 * @param entity
	 * @return
	 */
	@RequestMapping("/test.do")
	public String goToTest(Model model,String entityName) {
		try {
			Class<?> c = Class.forName("entity." + entityName);
			String String1 = MySpringUtil.getClassAttrs(c);
			Map<String, Object> map = new HashMap<String, Object>();
			String String2 = DebugUtil.getJavaFile(c, request);
			String[] strings1 = String1.replace("serialVersionUID,", "").replace(",serialVersionUID", "").split(",");
			String[] strings2 =  String2.split("</br>\r\n");
			for (int i = 0; i < strings1.length; i++) {
				if(strings1.length == strings2.length) {
					map.put(strings1[i], strings2[i]);
				} else {
					map.put(strings1[i], strings1[i]);
				}
			}
			model.addAttribute("fields", getJson(map));
			Class<?> c1 = getClass(entityName);
			model.addAttribute("control", DebugUtil.getRequestMapping(c1, request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "test";
	}
	
	private Class<?> getClass(String className) {
		String proPath = "/flow";
		return MyUtil.getClass(proPath,className);
	}
	
	private Map<String, String> getEntities() {
		Map<String, String> map = new HashMap<String, String>();
		String path = "E:\\Workspaces\\MyEclipse Professional\\myFlow_1\\src\\org\\flow\\com\\business\\entity\\";
		List<String> list = FileUtil.scanFilePaths(path);
		List<Class<?>> cc = new ArrayList<Class<?>>();
		try {
			for(String s : list) {
				if(s.contains(".java")) {
					String str = "src\\org\\flow\\com\\business\\";
					cc.add(MyUtil.getClass(s.substring(s.indexOf(str) + str.length(),s.indexOf(".")).replace("\\", ".")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(Class<?> c : cc) {
			map.put(c.getSimpleName(), DebugUtil.getJavaFile(c, request));
		}
		return map;
	}
}
