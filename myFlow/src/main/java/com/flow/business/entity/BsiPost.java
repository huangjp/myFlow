package com.flow.business.entity;

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
@Table(name="BSI_POST")
public class BsiPost implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=11,nullable=false)
	private Integer id;

	@Column(name="NAME",length=5,nullable=true)
	private String name;

	@Column(name="DESC",length=11,nullable=false)
	private Integer desc;

	@Column(name="CLAIM",length=11,nullable=false)
	private Integer claim;

	@Column(name="CREATE_PERSON",length=11,nullable=false)
	private Integer createPerson;

	@Column(name="UPDATE_TIME",length=255,nullable=true)
	private String updateTime;

	@Column(name="PERFORMANCE",length=255,nullable=true)
	private String performance;

	@Transient
	private List<BsiUser> users;
	
	public List<BsiUser> getUsers() {
		return users;
	}

	public void setUsers(List<BsiUser> users) {
		this.users = users;
	}

	public void setId(Integer id){
		this.id=id;
	}
	
	public Integer getId(){
		return id;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setDesc(Integer desc){
		this.desc=desc;
	}
	
	public Integer getDesc(){
		return desc;
	}
	
	public void setClaim(Integer claim){
		this.claim=claim;
	}
	
	public Integer getClaim(){
		return claim;
	}
	
	public void setCreatePerson(Integer createPerson){
		this.createPerson=createPerson;
	}
	
	public Integer getCreatePerson(){
		return createPerson;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime=updateTime;
	}
	
	public String getUpdateTime(){
		return updateTime;
	}
	
	public void setPerformance(String performance){
		this.performance=performance;
	}
	
	public String getPerformance(){
		return performance;
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
		BsiPost other = (BsiPost) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

