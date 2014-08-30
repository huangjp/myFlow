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
@Table(name = "LINK_HAS_CONDITION")
public class LinkHasCondition implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", length = 20, nullable = false)
	private Long id;

	@Column(name = "CONDITION_ID", length = 20, nullable = false)
	private Long conditionId;

	@Column(name = "FLOW_INSTANCE_ASSIST_ID", length = 20, nullable = true)
	private Long flowInstanceAssistId;

	@Column(name = "CONDITION_TRUE_LINK_ID", length = 20, nullable = true)
	private Long conditionTrueLinkId;// 条件为真时绑定的环节ID

	@Column(name = "CONDITION_FALSE_LINK_ID", length = 20, nullable = true)
	private Long conditionFalseLinkId;// 条件为假时绑定的环节ID

	@Column(name = "FLOW_ASSIST_ID", length = 20, nullable = true)
	private Long flowAssistId;// 来自默认流程或者是公共流程以及自定义流程对条件环节的绑定关系

	public LinkHasCondition(Long flowInstanceAssistId, Long flowAssistId) {
		super();
		this.flowInstanceAssistId = flowInstanceAssistId;
		this.flowAssistId = flowAssistId;
	}

	public LinkHasCondition(Long id) {
		super();
		this.id = id;
	}


	public LinkHasCondition() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setConditionId(Long conditionId) {
		this.conditionId = conditionId;
	}

	public Long getConditionId() {
		return conditionId;
	}

	public void setFlowInstanceAssistId(Long flowInstanceAssistId) {
		this.flowInstanceAssistId = flowInstanceAssistId;
	}

	public Long getFlowInstanceAssistId() {
		return flowInstanceAssistId;
	}

	public void setConditionTrueLinkId(Long conditionTrueLinkId) {
		this.conditionTrueLinkId = conditionTrueLinkId;
	}

	public Long getConditionTrueLinkId() {
		return conditionTrueLinkId;
	}

	public void setConditionFalseLinkId(Long conditionFalseLinkId) {
		this.conditionFalseLinkId = conditionFalseLinkId;
	}

	public Long getConditionFalseLinkId() {
		return conditionFalseLinkId;
	}

	public void setFlowAssistId(Long flowAssistId) {
		this.flowAssistId = flowAssistId;
	}

	public Long getFlowAssistId() {
		return flowAssistId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkHasCondition other = (LinkHasCondition) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
