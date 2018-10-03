package main.java.com.devrevolhope.mywallet.service;

import java.util.List;

import main.java.com.devrevolhope.mywallet.model.Directory;

public interface DirectoryService {

	List<Directory> findAll();
	
	Directory findById(long id);
	
	Directory findByName(String directoryName, long userId);
	
	Directory findRoot(long userId);
	
	Directory findShared(long userId);
	
	List<Directory> findDirectoriesAtRoot(long userId);
	
	Directory findParent(long dirId);
	
	void persist(Directory directory);
	
	void update(Directory directory);
	
	void remove(Directory directory);
	
}
