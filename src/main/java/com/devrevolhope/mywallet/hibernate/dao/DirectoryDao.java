package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import main.java.com.devrevolhope.mywallet.model.Directory;

public interface DirectoryDao extends Serializable {
	
	List<Directory> findAll();
	
	Directory findById(Long id);
	
	Directory findByName(String name, long userId);
	
	List<Directory> findDirectoriesAtRoot(long userId);
	
	Directory findParent(long dirId);
	
	void save(Directory directory);
		
	void remove(Directory directory);
}
