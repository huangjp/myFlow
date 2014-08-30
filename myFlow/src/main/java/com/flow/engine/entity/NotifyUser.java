package com.flow.engine.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Table;

@Entity
@Table(name="NOTIFY_USER")
public class NotifyUser implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;
	
	@Column(name="LINK_ID",length=20,nullable=true)
	private Long linkId;

	@Column(name="NOTIFY_USER_ID",length=20,nullable=false)
	private Long notifyUserId;//知会执行ID，可以是群组执行、个人执行、岗位执行

	@Column(name="NOTIFY_ID",length=20,nullable=true)
	private Long notifyId;

	@Column(name="TYPE_ID",length=20,nullable=false)
	private Long typeId;
	
	public NotifyUser(Long linkId, Long userId) {
		super();
		this.linkId = linkId;
		this.notifyUserId = userId;
	}

	public NotifyUser() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLinkId(Long linkId){
		this.linkId=linkId;
	}
	
	public Long getLinkId(){
		return linkId;
	}
	
	public void setNotifyUserId(Long notifyUserId){
		this.notifyUserId=notifyUserId;
	}
	
	public Long getNotifyUserId(){
		return notifyUserId;
	}
	
	public void setNotifyId(Long notifyId){
		this.notifyId=notifyId;
	}
	
	public Long getNotifyId(){
		return notifyId;
	}
	
	public void setTypeId(Long typeId){
		this.typeId=typeId;
	}
	
	public Long getTypeId(){
		return typeId;
	}
	@Override	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((linkId == null) ? 0 : linkId.hashCode());
		return result;
	}

	@Override	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		NotifyUser other = (NotifyUser) obj;
		if (linkId == null) {
			if (other.linkId != null) return false;
		} else if (!linkId.equals(other.linkId)) return false;
		return true;
	}

}

