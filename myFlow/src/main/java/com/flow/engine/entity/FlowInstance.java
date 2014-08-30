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
@Table(name="FLOW_INSTANCE")
public class FlowInstance implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;

	@Column(name="CURRENT_LINK_ID",length=20,nullable=true)
	private Long currentLinkId;//流程当前环节ID

	@Column(name="NEXT_LINK_ID",length=20,nullable=true)
	private Long nextLinkId;//流程下一个环节,(0)-有子流程开启;(-1)-流程结束;

	@Column(name="FLOW_INSTANCE_ID",length=20,nullable=false)
	private Long flowInstanceId;//父流程

	@Column(name="CRETAE_USER_ID",length=20,nullable=true)
	private Long cretaeUserId;//流程实例的创建人

	@Column(name="FLOW_ID",length=20,nullable=false)
	private Long flowId;

	public FlowInstance(Long id, Long userId) {
		super();
		this.id = id;
		this.cretaeUserId = userId;
	}

	public FlowInstance() {
		super();
	}

	public void setId(Long id){
		this.id=id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setCurrentLinkId(Long currentLinkId){
		this.currentLinkId=currentLinkId;
	}
	
	public Long getCurrentLinkId(){
		return currentLinkId;
	}
	
	public void setNextLinkId(Long nextLinkId){
		this.nextLinkId=nextLinkId;
	}
	
	public Long getNextLinkId(){
		return nextLinkId;
	}
	
	public void setFlowInstanceId(Long flowInstanceId){
		this.flowInstanceId=flowInstanceId;
	}
	
	public Long getFlowInstanceId(){
		return flowInstanceId;
	}
	
	public void setCretaeUserId(Long cretaeUserId){
		this.cretaeUserId=cretaeUserId;
	}
	
	public Long getCretaeUserId(){
		return cretaeUserId;
	}
	
	public void setFlowId(Long flowId){
		this.flowId=flowId;
	}
	
	public Long getFlowId(){
		return flowId;
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
		FlowInstance other = (FlowInstance) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

