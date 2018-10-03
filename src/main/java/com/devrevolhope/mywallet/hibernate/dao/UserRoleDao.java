package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import main.java.com.devrevolhope.mywallet.model.AppUserRole;

public interface UserRoleDao extends Serializable {
	
	List<AppUserRole> findAll();
	
	AppUserRole findById(Long id);
	
	AppUserRole findByType(String type);
	
	void save(AppUserRole role);
		
	void remove(AppUserRole role);
}
