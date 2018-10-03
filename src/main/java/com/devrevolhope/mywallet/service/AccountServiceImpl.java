package main.java.com.devrevolhope.mywallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.devrevolhope.mywallet.hibernate.dao.AccountDao;
import main.java.com.devrevolhope.mywallet.model.Account;

@Transactional
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao dao;
	
	@Override
	public void update(Account account) {
		Account entity = dao.findById(account.getId());
        if(entity!=null){
        	entity.setName(account.getName());
        	entity.setEmail(account.getEmail());
        	entity.setDescription(account.getDescription());
        	entity.setKey(account.getKey());
        	entity.setMetadata(account.getMetadata());
        	entity.setDirectory(account.getDirectory());
        	entity.setCreationDate(account.getCreationDate());
        	entity.setUpdatedDate(account.getUpdatedDate());
        }
	}

	@Override
	public Account findById(long id) {
		return dao.findById(id);
	}
	
	@Override
	public void persist(Account account) {
		dao.save(account);
	}
	
	@Override
	public void remove(Account account) {
		dao.remove(account);
	}
}
