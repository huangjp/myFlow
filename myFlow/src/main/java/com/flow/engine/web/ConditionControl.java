package com.flow.engine.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flow.common.util.BaseController;
import com.flow.engine.entity.Condition;
import com.flow.engine.service.IConditionService;

@Controller
public class ConditionControl extends BaseController {

	@Autowired
	private IConditionService conditionService;
	
	@RequestMapping(value="/saveCondition.do", method = RequestMethod.POST)
	public @ResponseBody String saveCondition(Condition condition) {
		Long id = this.conditionService.saveCondition(condition);
		return getJson(id);
	}
	
	@RequestMapping(value="/deleteCondition.do", method = RequestMethod.POST)
	public @ResponseBody String deleteCondition(Condition condition) {
		boolean bool = this.conditionService.deleteCondition(condition);
		return getJson(bool);
	}
	
	@RequestMapping(value="/updateCondition.do",method=RequestMethod.POST)
	public @ResponseBody String updateCondition(Condition condition) {
		boolean bool = this.conditionService.updateCondition(condition);
		return getJson(bool);
	}
	
	@RequestMapping(value="/getyCondition.do",method=RequestMethod.POST)
	public @ResponseBody String getCondition(Condition condition) {
		condition = this.conditionService.getCondition(condition);
		return getJson(condition);
	}
	
	@RequestMapping(value="/getyConditions.do",method=RequestMethod.POST)
	public @ResponseBody String getConditions(Condition condition) {
		List<Condition> conditions = this.conditionService.getConditions(condition);
		return getJson(conditions);
	}
}
