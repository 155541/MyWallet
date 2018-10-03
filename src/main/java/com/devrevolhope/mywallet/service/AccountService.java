package main.java.com.devrevolhope.mywallet.service;

import main.java.com.devrevolhope.mywallet.model.Account;

public interface AccountService {
	
	void update(Account account);
	
	Account findById(long id);
	
	void persist(Account account);
	
	void remove(Account account);
}
