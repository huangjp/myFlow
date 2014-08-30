package service;

import java.util.List;

import entity.ExecuteUser;
import entity.Link;
import entity.NotifyUser;

public interface ILinkService {

	Long saveLink(Link link, List<ExecuteUser> executeUsers, List<NotifyUser> notifyUsers);
	
	boolean deleteLink(Link link);
	
	boolean updateLink(Link link);
	
	Link getLink(Link link);
	
	List<Link> getLinks(Link link);
}
