package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="`CONDITION`")
public class Condition implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;

	@Column(name="CONDITION_DESC",length=45,nullable=true)
	private String conditionDesc;//条件描述

	@Column(name="`EXPRESSION`",length=45,nullable=true)
	private String expression;//条件表达式
	
	@Transient
	private Long linkId;//非数据库字段，确定的环节ID
	
	@Transient
	private boolean result;//非数据库字段，条件表达式的真假
	
	public Condition() {
		super();
	}

	public Condition(Long id) {
		super();
		this.id = id;
	}

	public void setId(Long id){
		this.id=id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setConditionDesc(String conditionDesc){
		this.conditionDesc=conditionDesc;
	}
	
	public String getConditionDesc(){
		return conditionDesc;
	}
	
	public void setExpression(String expression){
		this.expression=expression;
	}
	
	public String getExpression(){
		return expression;
	}


	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
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
		Condition other = (Condition) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}
}

