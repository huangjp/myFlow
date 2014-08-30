package service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import base.DaoService;

import service.ILinkService;
import entity.ExecuteUser;
import entity.Link;
import entity.NotifyUser;

@Service
public class LinkService extends DaoService implements ILinkService {
	
	@Override
	public Long saveLink(Link link, List<ExecuteUser> executeUsers, List<NotifyUser> notifyUsers) {
		
		if(link == null) {
			throw new RuntimeException("save the link cannot be empty");
		}
		
		try {
			Long id = save(link);
			if(executeUsers != null && !executeUsers.isEmpty()) {
				for (NotifyUser user : notifyUsers) {
					user.setLinkId(id);
					save(user);
				}
			}
			if(notifyUsers != null && !notifyUsers.isEmpty()) {
				for(ExecuteUser user : executeUsers) {
					user.setLinkId(id);
					save(user);
				}
			}
			return id;
		} catch (Exception e) {
			throw new RuntimeException("link save failed, transaction should be rolled back");
		}
	}

	@Override
	public boolean deleteLink(Link link) {
		
		if(link == null || link.getId() == null) {
			throw new RuntimeException("link or link ID can not empty");
		}
		
		try {
			delete(link);
			delete(new ExecuteUser(link.getId(), null));
			delete(new NotifyUser(link.getId(), null));
		} catch (Exception e) {
			throw new RuntimeException("link to remove failure, transaction should be rolled back");
		}
		
		return true;
	}

	@Override
	public boolean updateLink(Link link) {
		
		if(link == null || link.getId() == null) {
			throw new RuntimeException("link or link ID can not empty");
		}
		
		try {
			update(link);
		} catch (Exception e) {
			throw new RuntimeException("link change failure, transaction should be rolled back");
		}
		
		return true;
	}

	@Override
	public Link getLink(Link link) {
		try {
			if(null == link) {
				return null;
			} else {
				return get(link);
			}
		} catch (Exception e) {
			throw new RuntimeException("Query link fails, the transaction should be rolled back");
		}
	}

	@Override
	public List<Link> getLinks(Link link) {
		try {
			if(null == link) {
				return getList(Link.class);
			} else {
				return getList(link);
			}
		} catch (Exception e) {
			throw new RuntimeException("Collection of query link fails, the transaction should be rolled back");
		}
	}

}
