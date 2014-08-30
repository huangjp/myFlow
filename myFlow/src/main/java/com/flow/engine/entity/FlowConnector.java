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
@Table(name="FLOW_CONNECTOR")
public class FlowConnector implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;

	@Column(name="USER_ID",length=20,nullable=true)
	private Long userId;//流程使用方的ID，可以是群组group、or用户user、or岗位post等等的ID

	@Column(name="FLOW_ID",length=20,nullable=false)
	private Long flowId;

	@Column(name="TYPE_ID",length=20,nullable=false)
	private Long typeId;//环节的执行类型ID

	
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
	
	public void setFlowId(Long flowId){
		this.flowId=flowId;
	}
	
	public Long getFlowId(){
		return flowId;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		FlowConnector other = (FlowConnector) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

