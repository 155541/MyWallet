package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import main.java.com.devrevolhope.mywallet.model.Directory;

@Repository("directoryDao")
public class DirectoryDaoImpl extends AbstractDao<Long, Directory> implements DirectoryDao{

	private static final long serialVersionUID = -5354916697433636888L;

	@Override
    public Directory findById (Long id) {
		Directory directory = super.findById(id);
		if(directory!=null){
            Hibernate.initialize(directory.getChildren());
            Hibernate.initialize(directory.getAccounts());
            Hibernate.initialize(directory.getParent());
        }
        return directory;
    }
	
	@Override
	public List<Directory> findAll() {
		
		CriteriaQuery<Directory> query = super.createCriteria();
		Root<Directory> root = query.from(Directory.class);
        query.select(root);
        Query<Directory> q = getSession().createQuery(query);
        if (q!=null)
        {
        	List<Directory> list = q.getResultList();
        	if (list != null)
        	{
        		for(Directory directory : list)
                {
                	Hibernate.initialize(directory.getChildren());
                	Hibernate.initialize(directory.getAccounts());
                	Hibernate.initialize(directory.getParent());
                }
        		return list;
        	}
        }
        return new ArrayList<Directory>();
	}

	@Override
	public Directory findByName(String name, long userId) {
		
		CriteriaQuery<Directory> query = super.createCriteria();
    	CriteriaBuilder builder = getSession().getCriteriaBuilder();
    	Root<Directory> root = query.from(Directory.class);
    	
    	query.select(root)
    			.where(builder.and(
    					builder.equal(root.get("name"), name),
    					builder.equal(root.get("owner"), userId)));
    	query.orderBy(builder.asc(root.get("name")));
    	
    	Query<Directory> q = getSession().createQuery(query);
    	Directory directory = q.getSingleResult();
    	if(directory!=null){
            Hibernate.initialize(directory.getChildren());
            Hibernate.initialize(directory.getAccounts());
            Hibernate.initialize(directory.getParent());
        }
        return directory;
	}

	public List<Directory> findDirectoriesAtRoot(long userId)
	{
		CriteriaQuery<Directory> query = super.createCriteria();
    	CriteriaBuilder builder = getSession().getCriteriaBuilder();
    	Root<Directory> root = query.from(Directory.class);
    	
    	query.select(root)
    		 .where(builder.and(
    				 builder.equal(root.get("owner"), userId),
    				 builder.isNull(root.get("parent"))));
    	query.orderBy(builder.asc(root.get("name")));
    	
    	
    	Query<Directory> q = getSession().createQuery(query);
    	List<Directory> list = q.getResultList();
    	
    	if (list != null)
    	{
    		for (Directory dir : list)
    		{
    			if(dir!=null){
    	            Hibernate.initialize(dir.getChildren());
    	            Hibernate.initialize(dir.getAccounts());
    	            Hibernate.initialize(dir.getParent());
    	        }
    		}
    		return list;
    	}
    	return null;
	}
	
	@Override
	public Directory findParent(long dirId) {
		
		CriteriaQuery<Directory> query = super.createCriteria();
    	CriteriaBuilder builder = getSession().getCriteriaBuilder();
    	Root<Directory> root = query.from(Directory.class);
    	
    	query.select(root).where(builder.equal(root.get("id"), dirId));
    	Query<Directory> q = getSession().createQuery(query);
    	Directory directory = q.getSingleResult();
    	if(directory!=null){
            Hibernate.initialize(directory.getChildren());
            Hibernate.initialize(directory.getAccounts());
            Hibernate.initialize(directory.getParent());
        }
    	
        return directory;
	}

	@Override
	public void save(Directory directory) {
		super.persist(directory);
	}

	@Override
	public void remove(Directory directory) {
		super.delete(directory);
	}

}
