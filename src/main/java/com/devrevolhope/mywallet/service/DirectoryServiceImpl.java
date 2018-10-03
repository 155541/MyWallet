package main.java.com.devrevolhope.mywallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.devrevolhope.mywallet.hibernate.dao.DirectoryDao;
import main.java.com.devrevolhope.mywallet.model.Directory;

@Transactional
@Service("directoryService")
public class DirectoryServiceImpl implements DirectoryService{

	@Autowired
	private DirectoryDao dao;
	
	@Override
	public List<Directory> findAll() {
		return dao.findAll();
	}

	@Override
	public Directory findById(long id) {
		return dao.findById(id);
	}

	@Override
	public Directory findByName(String directoryName, long userId) {
		return dao.findByName(directoryName, userId);
	}

	@Override
	public Directory findRoot(long userId) {
		return dao.findByName("HOME", userId);
	}
	
	@Override
	public Directory findShared(long userId) {
		return dao.findByName("SHARED", userId);
	}
	
	@Override
	public List<Directory> findDirectoriesAtRoot(long userId) {
		return dao.findDirectoriesAtRoot(userId);
	}

	@Override
	public Directory findParent(long dirId) {
		return dao.findParent(dirId);
	}

	@Override
	public void persist(Directory directory) {
		dao.save(directory);
	}

	@Override
	public void update(Directory directory) {
		Directory entity = dao.findById(directory.getId());
        if(entity!=null){
        	entity.setName(directory.getName());
        	entity.setOwner(directory.getOwner());
        	entity.setChildren(directory.getChildren());
        	entity.setAccounts(directory.getAccounts());
        	entity.setParent(directory.getParent());
        }
	}

	@Override
	public void remove(Directory directory) {
		dao.remove(directory);
	}
}
