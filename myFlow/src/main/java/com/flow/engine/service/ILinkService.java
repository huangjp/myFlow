package com.flow.engine.service;

import java.util.List;

import com.flow.engine.entity.ExecuteUser;
import com.flow.engine.entity.Link;
import com.flow.engine.entity.NotifyUser;

public interface ILinkService {

	Long saveLink(Link link, List<ExecuteUser> executeUsers, List<NotifyUser> notifyUsers);
	
	boolean deleteLink(Link link);
	
	boolean updateLink(Link link);
	
	Link getLink(Link link);
	
	List<Link> getLinks(Link link);
}
