package com.flow.business.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Table;

@Entity
@Table(name="BSI_USER_HAS_GROUP")
public class BsiUserHasGroup implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id",length=11,nullable=false)
	private Integer userId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="GROUP_ID",length=11,nullable=false)
	private Integer groupId;

	
	public void setUserId(Integer userId){
		this.userId=userId;
	}
	
	public Integer getUserId(){
		return userId;
	}
	
	public void setGroupId(Integer groupId){
		this.groupId=groupId;
	}
	
	public Integer getGroupId(){
		return groupId;
	}
	@Override	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BsiUserHasGroup other = (BsiUserHasGroup) obj;
		if (userId == null) {
			if (other.userId != null) return false;
		} else if (!userId.equals(other.userId)) return false;
		return true;
	}

}

