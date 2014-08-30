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
@Table(name="FLOW_INSTANCE_ASSIST")
public class FlowInstanceAssist implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;

	@Column(name="LINK_ID",length=20,nullable=false)
	private Long linkId;//环节ID

	@Column(name="FLOW_INSTANCE_ID",length=20,nullable=false)
	private Long flowInstanceId;

	@Transient
	private Long oldLink;//原环节ID
	
	public FlowInstanceAssist() {
		super();
	}

	public FlowInstanceAssist(Long linkId, Long flowInstanceId) {
		super();
		this.linkId = linkId;
		this.flowInstanceId = flowInstanceId;
	}

	public FlowInstanceAssist(Long flowInstanceId) {
		super();
		this.flowInstanceId = flowInstanceId;
	}

	public void setId(Long id){
		this.id=id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setLinkId(Long linkId){
		this.linkId=linkId;
	}
	
	public Long getLinkId(){
		return linkId;
	}
	
	public void setFlowInstanceId(Long flowInstanceId){
		this.flowInstanceId=flowInstanceId;
	}
	
	public Long getFlowInstanceId(){
		return flowInstanceId;
	}
	
	public Long getOldLink() {
		return oldLink;
	}

	public void setOldLink(Long oldLink) {
		this.oldLink = oldLink;
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
		FlowInstanceAssist other = (FlowInstanceAssist) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

