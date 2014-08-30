package entity;

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
@Table(name="PROCESS_OPERATOR")
public class ProcessOperator implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;
	
	@Column(name="PROCESS_ID",length=20,nullable=true)
	private Long processId;

	@Column(name="USER_ID",length=20,nullable=true)
	private Long userId;//用户ID

	@Column(name="LINK_CONDITION",length=45,nullable=true)
	private String linkCondition;//环节里的每个人都有自己的条件表达式

	@Column(name="OPERATOR_DESC",length=45,nullable=true)
	private String operatorDesc;//每个人都有其操作意见
	
	@Transient
	private List<String> linkConditions; //多个条件表达式

	public ProcessOperator(Long processId, Long userId) {
		this.processId = processId;
		this.userId = userId;
	}

	public ProcessOperator() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProcessId(Long processId){
		this.processId=processId;
	}
	
	public Long getProcessId(){
		return processId;
	}
	
	public void setUserId(Long userId){
		this.userId=userId;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public void setLinkCondition(String linkCondition){
		this.linkCondition=linkCondition;
	}
	
	public String getLinkCondition(){
		return linkCondition;
	}
	
	public void setOperatorDesc(String operatorDesc){
		this.operatorDesc=operatorDesc;
	}
	
	public String getOperatorDesc(){
		return operatorDesc;
	}
	
	public List<String> getLinkConditions() {
		return linkConditions;
	}

	public void setLinkConditions(List<String> linkConditions) {
		this.linkConditions = linkConditions;
	}

	@Override	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processId == null) ? 0 : processId.hashCode());
		return result;
	}

	@Override	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ProcessOperator other = (ProcessOperator) obj;
		if (processId == null) {
			if (other.processId != null) return false;
		} else if (!processId.equals(other.processId)) return false;
		return true;
	}

}

