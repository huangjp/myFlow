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
@Table(name="FLOW")
public class Flow implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;

	@Column(name="`NAME`",length=45,nullable=true)
	private String name;//流程名称

	@Column(name="`TYPE`",length=45,nullable=true)
	private String type;//流程类型

	@Column(name="`LEVEL`",length=1,nullable=true)
	private Integer level;//0-默认；1-自定义个人流程；2-自定义公共流程

	@Column(name="`DESCRIBE`",length=45,nullable=true)
	private String describe;//流程描述，通常用表述这个流程的适用范围
	
	public Flow(Long id) {
		super();
		this.id = id;
	}

	public Flow() {
		super();
	}

	public void setId(Long id){
		this.id=id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setType(String type){
		this.type=type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setLevel(Integer level){
		this.level=level;
	}
	
	public Integer getLevel(){
		return level;
	}
	
	public void setDescribe(String describe){
		this.describe=describe;
	}
	
	public String getDescribe(){
		return describe;
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
		Flow other = (Flow) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

