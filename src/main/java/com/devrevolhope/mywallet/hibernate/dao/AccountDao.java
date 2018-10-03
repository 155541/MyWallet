package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.io.Serializable;

import main.java.com.devrevolhope.mywallet.model.Account;

public interface AccountDao extends Serializable {
	
	Account findById(long id);
	
	void save(Account account);
	
	void remove(Account account);
}
