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
@Table(name="BUSINESS")
public class Business implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;

	@Column(name="TYPE",length=45,nullable=true)
	private String type;//业务类型

	@Column(name="BUSINESS_ID",length=45,nullable=true)
	private String businessId;//外部业务ID

	@Column(name="BUSINESS_CONDITION",length=45,nullable=true)
	private String businessCondition;//当页面传来条件时，可以去查看是否该业务下有合适的流程以供其使用，如果没有，则需要新建流程定义
	
	public Business(String businessId) {
		super();
		this.businessId = businessId;
	}

	public Business() {
		super();
	}

	public Business(Long id) {
		super();
		this.id = id;
	}

	public void setId(Long id){
		this.id=id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setType(String type){
		this.type=type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setBusinessId(String businessId){
		this.businessId=businessId;
	}
	
	public String getBusinessId(){
		return businessId;
	}
	
	public void setBusinessCondition(String businessCondition){
		this.businessCondition=businessCondition;
	}
	
	public String getBusinessCondition(){
		return businessCondition;
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
		Business other = (Business) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

