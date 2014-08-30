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
@Table(name="FLOW_ASSIST")
public class FlowAssist implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=20,nullable=false)
	private Long id;

	@Column(name="FLOW_ID",length=20,nullable=false)
	private Long flowId;//如果一条流程是默认或是公用流程，将不需要创建流程实例则可以直接使用，在使用时也不需要确定该流程的环节设定和条件设定

	@Column(name="LINK_ID",length=20,nullable=false)
	private Long linkId;

	public FlowAssist(Long linkId, Long flowId) {
		super();
		this.linkId = linkId;
		this.flowId = flowId;
	}

	public FlowAssist() {
		super();
	}

	public void setId(Long id){
		this.id=id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setFlowId(Long flowId){
		this.flowId=flowId;
	}
	
	public Long getFlowId(){
		return flowId;
	}
	
	public void setLinkId(Long linkId){
		this.linkId=linkId;
	}
	
	public Long getLinkId(){
		return linkId;
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
		FlowAssist other = (FlowAssist) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

