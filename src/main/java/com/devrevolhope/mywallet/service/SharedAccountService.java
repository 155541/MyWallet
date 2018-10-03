package main.java.com.devrevolhope.mywallet.service;

import java.util.List;
import java.util.Map;
import main.java.com.devrevolhope.mywallet.model.SharedAccount;

public interface SharedAccountService {

	Map<Long,List<SharedAccount>> findAllSharings(long userId);
	
	List<SharedAccount> findSharedFrom(long id, long userId);
	
	boolean isAccountShared(long idAccount, long userId);
	
	void persist(SharedAccount sharedAccount);
	
	void update(SharedAccount sharedAccount);
	
	void remove(SharedAccount sharedAccount);
}
