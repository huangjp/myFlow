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
@Table(name="BSI_GROUP_HAS_POST")
public class BsiGroupHasPost implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="GROUP_ID",length=11,nullable=false)
	private Integer groupId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="POST_ID",length=11,nullable=false)
	private Integer postId;

	
	public void setGroupId(Integer groupId){
		this.groupId=groupId;
	}
	
	public Integer getGroupId(){
		return groupId;
	}
	
	public void setPostId(Integer postId){
		this.postId=postId;
	}
	
	public Integer getPostId(){
		return postId;
	}
	@Override	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		return result;
	}

	@Override	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BsiGroupHasPost other = (BsiGroupHasPost) obj;
		if (groupId == null) {
			if (other.groupId != null) return false;
		} else if (!groupId.equals(other.groupId)) return false;
		return true;
	}

}

