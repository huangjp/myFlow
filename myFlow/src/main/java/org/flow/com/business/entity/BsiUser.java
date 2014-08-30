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
@Table(name="BSI_USER")
public class BsiUser implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",length=11,nullable=false)
	private Integer id;

	@Column(name="name",length=5,nullable=true)
	private String name;

	@Column(name="user_name",length=5,nullable=true)
	private String userName;

	@Column(name="phone",length=20,nullable=false)
	private Long phone;

	@Column(name="email",length=25,nullable=true)
	private String email;

	@Column(name="password",length=11,nullable=false)
	private Integer password;

	@Column(name="picture",length=11,nullable=false)
	private Integer picture;

	
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
	
	public void setUserName(String userName){
		this.userName=userName;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setPhone(Long phone){
		this.phone=phone;
	}
	
	public Long getPhone(){
		return phone;
	}
	
	public void setEmail(String email){
		this.email=email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setPassword(Integer password){
		this.password=password;
	}
	
	public Integer getPassword(){
		return password;
	}
	
	public void setPicture(Integer picture){
		this.picture=picture;
	}
	
	public Integer getPicture(){
		return picture;
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
		BsiUser other = (BsiUser) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

