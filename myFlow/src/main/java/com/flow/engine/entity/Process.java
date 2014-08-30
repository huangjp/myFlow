package com.flow.engine.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Table;

@Entity
@Table(name="`PROCESS`")
public class Process implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;

	@Column(name="CURRENT_LINK_ID",length=20,nullable=true)
	private Long currentLinkId;//流程当前环节

	@Column(name="IS_SHOW",length=1,nullable=true)//default="b'1'"
	private Boolean isShow;//该状态是否需要显示

	@Column(name="FLOW_INSTANCE_ID",length=20,nullable=false)
	private Long flowInstanceId;//流程实例ID

	@Column(name="BUSINESS_ID",length=20,nullable=false)
	private Long businessId;//内部业务ID，即BUSINESS_ID
	
	@Column(name="BEFORE_ID", length = 20, nullable=false)
	private Long beforeId;//前一条记录
	
	@Transient
	private List<ProcessOperator> operators;//每个人的操作记录 

	public Long getBeforeId() {
		return beforeId;
	}

	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

	public List<ProcessOperator> getOperators() {
		return operators;
	}

	public void setOperators(List<ProcessOperator> operators) {
		this.operators = operators;
	}

	public Process(Long flowInstanceId, Long id) {
		this.flowInstanceId = flowInstanceId;
		this.id = id;
	}

	public Process() {
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
	
	public void setIsShow(Boolean isShow){
		this.isShow=isShow;
	}
	
	public Boolean getIsShow(){
		return isShow;
	}
	
	public void setFlowInstanceId(Long flowInstanceId){
		this.flowInstanceId=flowInstanceId;
	}
	
	public Long getFlowInstanceId(){
		return flowInstanceId;
	}
	
	public void setBusinessId(Long businessId){
		this.businessId=businessId;
	}
	
	public Long getBusinessId(){
		return businessId;
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
		Process other = (Process) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

