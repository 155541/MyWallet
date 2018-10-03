package main.java.com.devrevolhope.mywallet.service;

import java.util.List;

import main.java.com.devrevolhope.mywallet.model.AppUserRole;

public interface UserRoleService {
	
	List<AppUserRole> findAll();
	
	AppUserRole findById(Long id);
	
	AppUserRole findByType(String type);
	
	void persist(AppUserRole role);
	
	void update(AppUserRole role);
	
	void remove(AppUserRole role);
}
