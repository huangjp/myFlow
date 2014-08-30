package web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ILinkService;
import util.BaseController;
import entity.ExecuteUser;
import entity.Link;
import entity.NotifyUser;

public class LinkControl extends BaseController {
	
	@Autowired
	private ILinkService linkService;
	
	@RequestMapping(value="/saveLink.do", method = RequestMethod.POST)
	public @ResponseBody String saveLink(Link link, List<ExecuteUser> executeUsers, List<NotifyUser> notifyUsers) {
		Long id = this.linkService.saveLink(link, executeUsers, notifyUsers);
		return getJson(id);
	}
	
	@RequestMapping(value="/deleteLink.do", method = RequestMethod.POST)
	public @ResponseBody String deleteLink(Link link) {
		boolean bool = this.linkService.deleteLink(link);
		return getJson(bool);
	}
	
	@RequestMapping(value="/updateLink.do",method=RequestMethod.POST)
	public @ResponseBody String updateLink(Link link) {
		boolean bool = this.linkService.updateLink(link);
		return getJson(bool);
	}
	
	@RequestMapping(value="/getyLink.do",method=RequestMethod.POST)
	public @ResponseBody String getLink(Link link) {
		link = this.linkService.getLink(link);
		return getJson(link);
	}
	
	@RequestMapping(value="/getyLinks.do",method=RequestMethod.POST)
	public @ResponseBody String getLinks(Link link) {
		List<Link> links = this.linkService.getLinks(link);
		return getJson(links);
	}
}
