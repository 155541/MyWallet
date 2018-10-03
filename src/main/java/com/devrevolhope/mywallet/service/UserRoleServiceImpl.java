package main.java.com.devrevolhope.mywallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.devrevolhope.mywallet.hibernate.dao.UserRoleDao;
import main.java.com.devrevolhope.mywallet.model.AppUserRole;

@Transactional
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao dao;
	
	@Override
	public List<AppUserRole> findAll() {
		return dao.findAll();
	}

	@Override
	public AppUserRole findById(Long id) {
		return dao.findById(id);
	}
	@Override
	public AppUserRole findByType(String type) {
		return dao.findByType(type);
	}

	@Override
	public void persist(AppUserRole role) {
		dao.save(role);
	}

	@Override
	public void update(AppUserRole role) {
		AppUserRole entity = dao.findById(role.getId());
		if (entity != null)
		{
			entity.setType(role.getType());
			entity.setUsersAssigned(role.getUsersAssigned());
		}
	}

	@Override
	public void remove(AppUserRole role) {
		dao.remove(role);
	}
}
