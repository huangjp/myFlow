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
@Table(name="EXECUTE_USER")
public class ExecuteUser implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;
	
	@Column(name="LINK_ID",length=20,nullable=true)
	private Long linkId;

	@Column(name="EXECUTE_USER_ID",length=20,nullable=true)
	private Long executeUserId;

	@Column(name="EXECUTE_ID",length=20,nullable=true)
	private Long executeId;//审批执行ID，可以是群组执行、个人执行、岗位执行

	@Column(name="TYPE_ID",length=20,nullable=false)
	private Long typeId;

	public ExecuteUser(Long linkId, Long userId) {
		super();
		this.linkId = linkId;
		this.executeUserId = userId; 
	}

	public ExecuteUser() {
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
	
	public void setExecuteUserId(Long executeUserId){
		this.executeUserId=executeUserId;
	}
	
	public Long getExecuteUserId(){
		return executeUserId;
	}
	
	public void setExecuteId(Long executeId){
		this.executeId=executeId;
	}
	
	public Long getExecuteId(){
		return executeId;
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
		ExecuteUser other = (ExecuteUser) obj;
		if (linkId == null) {
			if (other.linkId != null) return false;
		} else if (!linkId.equals(other.linkId)) return false;
		return true;
	}

}

