package main.java.com.devrevolhope.mywallet.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="LOG", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"STAMP"})})
public class Log implements Serializable
{
	private static final long serialVersionUID = 3911739414695459671L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", columnDefinition = "NUMERIC(19,0)")
	private Long id;
	
	@Column(name="STAMP", nullable=false, unique=true)
	private int stamp;
	
	@Column(name="DATE", unique=false, nullable=false)
	private String date;
	
	@Column(name="ACTION", length=10, unique=false, nullable=false)
	private String action;
	
	@ManyToOne
    @JoinColumn(name = "USER")
	private AppUser user;
	
	@ManyToOne
    @JoinColumn(name = "DIRECTORY_INVOLVED")
	private Directory directory;
	
	@ManyToOne
    @JoinColumn(name = "ACCOUNT_INVOLVED")
	private Account account;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	public int getStamp() {
		return stamp;
	}

	public void setStamp(int stamp) {
		this.stamp = stamp;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Directory getFolder() {
		return directory;
	}

	public void setFolder(Directory folder) {
		this.directory = folder;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
