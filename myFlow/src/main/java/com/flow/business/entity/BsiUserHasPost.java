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
@Table(name="BSI_USER_HAS_POST")
public class BsiUserHasPost implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id",length=11,nullable=false)
	private Integer userId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="POST_ID",length=11,nullable=false)
	private Integer postId;

	@Column(name="MAIN_POST",length=11,nullable=false)
	private Integer mainPost;

	
	public void setUserId(Integer userId){
		this.userId=userId;
	}
	
	public Integer getUserId(){
		return userId;
	}
	
	public void setPostId(Integer postId){
		this.postId=postId;
	}
	
	public Integer getPostId(){
		return postId;
	}
	
	public void setMainPost(Integer mainPost){
		this.mainPost=mainPost;
	}
	
	public Integer getMainPost(){
		return mainPost;
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
		BsiUserHasPost other = (BsiUserHasPost) obj;
		if (userId == null) {
			if (other.userId != null) return false;
		} else if (!userId.equals(other.userId)) return false;
		return true;
	}

}

