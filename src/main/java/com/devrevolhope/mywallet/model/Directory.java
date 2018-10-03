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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

@Entity
@Table(name="DIRECTORY", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"NAME"})})
public class Directory implements Serializable
{
	private static final long serialVersionUID = 2633824121873648494L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", columnDefinition = "NUMERIC(19,0)")
	private Long id;
	
	@Column(name="NAME", length=50, unique=true, nullable=true)
	private String name;
	
	@Valid
	@OneToMany(orphanRemoval = true, mappedBy="parent")
	private Set<Directory> children = new HashSet<Directory>();
	
	@Valid
	@OneToMany(orphanRemoval = true, mappedBy="directory"/*, cascade=CascadeType.ALL*/)
	private Set<Account> accounts = new HashSet<Account>();
	
	@ManyToOne
    @JoinColumn(name = "ID_PARENT")
	private Directory parent;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_OWNER")
	private AppUser owner;
	
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
	public Set<Directory> getChildren() {
		return children;
	}
	public void setChildren(Set<Directory> children) {
		this.children = children;
	}
	public Directory getParent() {
		return parent;
	}
	public void setParent(Directory parent) {
		this.parent = parent;
	}
	public AppUser getOwner() {
		return owner;
	}
	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
}
