package com.flow.business.entity;

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
@Table(name="MENU")
public class Menu implements Serializable {

	@Transient	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID",length=11,nullable=false)
	private Integer id;

	@Column(name="NAME",length=45,nullable=false)
	private String name;//菜单名称

	@Column(name="POWER",length=45,nullable=true)
	private String power;//菜单级别，或者权限

	@Column(name="ICON",length=45,nullable=true)
	private String icon;//图标

	@Column(name="MENU_ID",length=11,nullable=true)
	private Integer menuId;//父ID
	
//	@OneToMany(mappedBy="menuId",cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	@Transient
	List<Menu> menus;
	
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
	
	public void setPower(String power){
		this.power=power;
	}
	
	public String getPower(){
		return power;
	}
	
	public void setIcon(String icon){
		this.icon=icon;
	}
	
	public String getIcon(){
		return icon;
	}
	
	public void setMenuId(Integer menuId){
		this.menuId=menuId;
	}
	
	public Integer getMenuId(){
		return menuId;
	}
	
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
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
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

