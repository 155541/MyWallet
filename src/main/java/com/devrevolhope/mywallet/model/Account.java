package main.java.com.devrevolhope.mywallet.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="ACCOUNT", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"NAME","_KEY"})})
public class Account implements Serializable
{
	
	private static final long serialVersionUID = -1454842722659271596L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", columnDefinition = "NUMERIC(19,0)")
	private Long id;
	
	@Column(name="NAME", length=50, unique=true, nullable=false)
	private String name;
	
	@Column(name="EMAIL", length=50, unique=false, nullable=true)
	private String email;
	
	@Column(name="DESCRIPTION", length=100, unique=false, nullable=true)
	private String description;
	
	@Column(name="_KEY", length=1000, unique=true, nullable=false)
	private String key;
	
	@Column(name="METADATA", length=50, unique=false, nullable=false)
	private String metadata;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECTORY_ID")
    private Directory directory;
	
	@Column(name="CREATION_DATE", columnDefinition = "NUMERIC(19,0)", nullable = false)
	private Long creationDate;
	
	@Column(name="UPDATED_DATE",columnDefinition = "NUMERIC(19,0)", nullable = true)
	private Long updatedDate;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	public Directory getDirectory() {
		return directory;
	}
	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
	public Long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}
	public Long getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Long updatedDate) {
		this.updatedDate = updatedDate;
	}
}
