package com.flow.business.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flow.business.entity.BsiGroup;
import com.flow.business.entity.BsiPost;
import com.flow.business.entity.BsiUser;
import com.flow.business.entity.MyBusiness;
import com.flow.business.entity.vo.BusinessDataVo;
import com.flow.business.service.IBusinessService;
import com.flow.common.util.BaseController;

@Controller
public class BusinessControl extends BaseController {

	@Autowired
	private IBusinessService service;
	
	@RequestMapping(value="/saveBusiness.do",method = RequestMethod.POST)
	public @ResponseBody String saveBusiness(BsiGroup group, BsiUser user, BsiPost post) {
		Map<Class<?>, Long> map = this.service.saveBusiness(group, user, post);
		return getJson(map);
	}
	
	@RequestMapping(value="/getGroupHasUser.do",method = RequestMethod.POST)
	public @ResponseBody String getGroupHasUser() {
		Object object = this.service.getGroupHasUser();
		return getJson(object);
	}
	
	@RequestMapping(value="/getPostHasUser.do", method = RequestMethod.POST)
	public @ResponseBody String getPostHasUser() {
		Object object = this.service.getPostHasUser();
		return getJson(object);
	}
	
	@RequestMapping(value="/getBusinesss.do",method=RequestMethod.POST)
	public @ResponseBody String getBusinesss() {
		BusinessDataVo flows = this.service.getBusinesses();
		return getJson(flows);
	}
	
	@RequestMapping(value="/getUsers.do", method = RequestMethod.POST)
	public @ResponseBody String getUsers() {
		Object object = this.getUsers();
		return getJson(object);
	}
	
	@RequestMapping(value="/saveMyBusiness.do",method = RequestMethod.POST)
	public @ResponseBody String saveBusiness(MyBusiness business) {
		Long id = this.service.saveBusiness(business);
		return getJson(id);
	}
	
	@RequestMapping(value="/updateMyBusiness.do",method=RequestMethod.POST)
	public @ResponseBody String updateBusiness(MyBusiness business) {
		boolean bool = this.service.updateBusiness(business);
		return getJson(bool);
	}
	
	@RequestMapping(value="/deleteBusiness.do",method=RequestMethod.POST)
	public @ResponseBody String deleteBusiness(MyBusiness business) {
		boolean bool = this.service.deleteBusiness(business);
		return getJson(bool);
	}
}
