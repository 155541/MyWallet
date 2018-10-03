package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import main.java.com.devrevolhope.mywallet.model.AppUser;

public interface UserDao extends Serializable {
	
	List<AppUser> findAll();
	
	AppUser findById(Long id);
	
	AppUser findByName(String name);
	
	void save(AppUser user);
		
	void remove(AppUser user);
}
