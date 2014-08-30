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
@Table(name="`MY_BUSINESS`")
public class MyBusiness implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="`ID`",length=20,nullable=false)
	private Long id;

	@Column(name="`USER_ID`",length=20,nullable=true)
	private Long userId;

	@Column(name="`TYPE`",length=255,nullable=true)
	private String type;

	@Column(name="`DESC`",length=255,nullable=true)
	private String desc;
	
	@Transient
	private Long flowId;
	
	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public void setId(Long id){
		this.id=id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setUserId(Long userId){
		this.userId=userId;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public void setType(String type){
		this.type=type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setDesc(String desc){
		this.desc=desc;
	}
	
	public String getDesc(){
		return desc;
	}
	@Override	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		MyBusiness other = (MyBusiness) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

