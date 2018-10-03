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

@Entity
@Table(name="SHARED_ACCOUNT")
public class SharedAccount implements Serializable {
	
	private static final long serialVersionUID = -642176808693501120L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", columnDefinition = "NUMERIC(19,0)")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OWNER")
    private AppUser accountOwner;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_SHARED")
    private AppUser userShared;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACCOUNT")
    private Account accountShared;
	
	@Column(name="DATE_SHARING", columnDefinition = "NUMERIC(19,0)", nullable = false)
	private Long dateSharing;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUser getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(AppUser accountOwner) {
		this.accountOwner = accountOwner;
	}

	public AppUser getUserShared() {
		return userShared;
	}

	public void setUserShared(AppUser userShared) {
		this.userShared = userShared;
	}

	public Account getAccountShared() {
		return accountShared;
	}

	public void setAccountShared(Account accountShared) {
		this.accountShared = accountShared;
	}

	public Long getDateSharing() {
		return dateSharing;
	}

	public void setDateSharing(Long dateSharing) {
		this.dateSharing = dateSharing;
	}
	
	public boolean isOwner(long id)
	{
		return accountOwner != null && accountOwner.getId().equals(id);
	}
}
