package main.java.com.devrevolhope.mywallet.hibernate.dao;

import org.springframework.stereotype.Repository;

import main.java.com.devrevolhope.mywallet.model.Account;

@Repository("accountDao")
public class AccountDaoImpl extends AbstractDao<Long, Account> implements AccountDao {

	private static final long serialVersionUID = -5067616094311721322L;

	
	@Override
	public Account findById(long id) {
		return super.findById(id);
	}
	
	@Override
	public void remove(Account account) {
		super.delete(account);
	}
	
	@Override
	public void save(Account account) {
		super.persist(account);
	}
}
