package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="BSI_GROUP")
public class BsiGroup implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=11,nullable=false)
	private Integer id;

	@Column(name="NAME",length=12,nullable=true)
	private String name;

	@Column(name="TYPE",length=5,nullable=true)
	private String type;

	@Column(name="NOTICE",nullable=true)
	private String notice;

	@Column(name="ADDRESS",length=255,nullable=true)
	private String address;

	@Column(name="GROUP_MANAGER",length=11,nullable=false)
	private Integer groupManager;

	@Column(name="IS_DEL",length=255,nullable=true)
	private String isDel;

	@Column(name="PARENT_ID",length=11,nullable=false)
	private Integer parentId;

	@Transient
	private List<BsiGroup> groups;
	
	@Transient
	private List<BsiPost> posts;
	
	@Transient
	private List<BsiUser> users;
	
	public List<BsiGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<BsiGroup> groups) {
		this.groups = groups;
	}

	public List<BsiPost> getPosts() {
		return posts;
	}

	public void setPosts(List<BsiPost> posts) {
		this.posts = posts;
	}

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
	
	public void setType(String type){
		this.type=type;
	}
	
	public String getType(){
		return type;
	}
	
	public void setNotice(String notice){
		this.notice=notice;
	}
	
	public String getNotice(){
		return notice;
	}
	
	public void setAddress(String address){
		this.address=address;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setGroupManager(Integer groupManager){
		this.groupManager=groupManager;
	}
	
	public Integer getGroupManager(){
		return groupManager;
	}
	
	public void setIsDel(String isDel){
		this.isDel=isDel;
	}
	
	public String getIsDel(){
		return isDel;
	}
	
	public void setParentId(Integer parentId){
		this.parentId=parentId;
	}
	
	public Integer getParentId(){
		return parentId;
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
		BsiGroup other = (BsiGroup) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

