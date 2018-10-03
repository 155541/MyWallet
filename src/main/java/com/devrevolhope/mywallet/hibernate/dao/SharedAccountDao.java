package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import main.java.com.devrevolhope.mywallet.model.SharedAccount;

public interface SharedAccountDao extends Serializable {
	
	SharedAccount findById(long id);
	
	Map<Long,List<SharedAccount>> findAllSharings(long userId);
	
	List<SharedAccount> findSharedFrom(long id, long userId);
	
	boolean isShared(long idAccount, long userId);
	
	void save(SharedAccount sharedAccount);
		
	void remove(SharedAccount sharedAccount);
}
