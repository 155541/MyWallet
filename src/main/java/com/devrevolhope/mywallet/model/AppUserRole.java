package main.java.com.devrevolhope.mywallet.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="APP_USER_ROLE", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"TYPE"})})
public class AppUserRole implements Serializable {

	private static final long serialVersionUID = 1236015381488866877L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", columnDefinition = "NUMERIC(19,0)")
	private Long id;
	
	@Column(name="TYPE", length=50, unique=true, nullable=false)
	private String type = AppUserRoleType.USER.getUserRoleType();

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "LINK_APP_USER__ROLE", 
             joinColumns = { @JoinColumn(name = "APP_USER_ROLE") }, 
             inverseJoinColumns = { @JoinColumn(name = "APP_USER_ID") })
    private Set<AppUser> usersAssigned = new HashSet<AppUser>();
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<AppUser> getUsersAssigned() {
		return usersAssigned;
	}

	public void setUsersAssigned(Set<AppUser> usersAssigned) {
		this.usersAssigned = usersAssigned;
	}
}
