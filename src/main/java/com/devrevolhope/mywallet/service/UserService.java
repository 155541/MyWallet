package main.java.com.devrevolhope.mywallet.service;

import java.util.List;

import main.java.com.devrevolhope.mywallet.model.AppUser;

public interface UserService {
	
	List<AppUser> findAll();
	
	AppUser findById(Long id);
	
	AppUser findByName(String username);
	
	void persist(AppUser user);
	
	void update(AppUser user);
	
	void remove(AppUser user);
}
