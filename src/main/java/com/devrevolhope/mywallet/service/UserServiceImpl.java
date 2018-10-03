package main.java.com.devrevolhope.mywallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.devrevolhope.mywallet.hibernate.dao.UserDao;
import main.java.com.devrevolhope.mywallet.model.AppUser;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public List<AppUser> findAll() {
		return dao.findAll();
	}

	@Override
	public void persist(AppUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}

	/*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
	@Override
	public void update(AppUser user) {
		AppUser entity = dao.findById(user.getId());
        if(entity!=null){
        	entity.setName(user.getName());
        	entity.setEmail(user.getEmail());
        	entity.setUserRoles(user.getUserRoles());
            if(!user.getPassword().equals(entity.getPassword()))
            {
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
	}

	@Override
	public void remove(AppUser user) {
		dao.remove(user);
	}

	@Override
	public AppUser findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public AppUser findByName(String username) {
		return dao.findByName(username);
	}
}
