package main.java.com.devrevolhope.mywallet.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.devrevolhope.mywallet.hibernate.dao.SharedAccountDao;
import main.java.com.devrevolhope.mywallet.model.SharedAccount;

@Transactional
@Service("sharedAccountService")
public class SharedAccountServiceImpl implements SharedAccountService{

	@Autowired
	private SharedAccountDao dao;
	
	@Override
	public Map<Long, List<SharedAccount>> findAllSharings(long userId) {
		return dao.findAllSharings(userId);
	}

	@Override
	public List<SharedAccount> findSharedFrom(long id, long userId) {
		return dao.findSharedFrom(id, userId);
	}

	@Override
	public void persist(SharedAccount sharedAccount) {
		dao.save(sharedAccount);
	}

	@Override
	public void update(SharedAccount sharedAccount) {
		SharedAccount entity = dao.findById(sharedAccount.getId());
        if(entity!=null){
        	entity.setUserShared(sharedAccount.getUserShared());
        	entity.setAccountOwner(sharedAccount.getAccountOwner());
        	entity.setAccountShared(sharedAccount.getAccountShared());
        	entity.setDateSharing(System.currentTimeMillis());
        }
	}

	@Override
	public void remove(SharedAccount sharedAccount) {
		dao.remove(sharedAccount);
	}

	@Override
	public boolean isAccountShared(long idAccount, long userId) {
		return dao.isShared(idAccount, userId);
	}
}
