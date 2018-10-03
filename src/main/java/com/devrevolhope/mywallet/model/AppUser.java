package main.java.com.devrevolhope.mywallet.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="APP_USER", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"NAME","EMAIL"})})
public class AppUser implements Serializable {
	
	private static final long serialVersionUID = 1808547722242590050L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", columnDefinition = "NUMERIC(19,0)")
	private Long id;
	
	@NotBlank(message = "Name cannot be blank.")
	@NotNull(message = "Name cannot be blank.")
	@Column(name="NAME", length=50, unique=true, nullable=false)
	private String name;
	
	@NotBlank(message = "Email cannot be blank.")
	@NotNull(message = "Email cannot be blank.")
	@Column(name="EMAIL", length=50, unique=true, nullable=false)
	private String email;
	
	@NotBlank(message = "Password cannot be blank.")
	@NotNull(message = "Password cannot be blank.")
	@Column(name="PASSWORD", length=100, unique=false, nullable=false)
	private String password;
	
	@Valid
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "LINK_APP_USER__ROLE", 
             joinColumns = { @JoinColumn(name = "APP_USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "APP_USER_ROLE") })
    private Set<AppUserRole> userRoles = new HashSet<AppUserRole>();
	
	@Valid
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy="owner")
	private Set<Directory> userDirectories = new HashSet<Directory>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<AppUserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<AppUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<Directory> getUserDirectories() {
		return userDirectories;
	}

	public void setUserDirectories(Set<Directory> userDirectories) {
		this.userDirectories = userDirectories;
	}
}
