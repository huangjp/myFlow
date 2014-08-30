package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Table;

@Entity
@Table(name="BUSINESS_CONDITION")
public class BusinessCondition implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;

	@Column(name="`EXPRESSION`",length=45,nullable=true)
	private String expression;//业务条件的表达式

	@Column(name="FLOW_ID",length=20,nullable=false)
	private Long flowId;

	
	public void setId(Long id){
		this.id=id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setExpression(String expression){
		this.expression=expression;
	}
	
	public String getExpression(){
		return expression;
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
		BusinessCondition other = (BusinessCondition) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

