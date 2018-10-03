package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import main.java.com.devrevolhope.mywallet.model.AppUser;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long, AppUser> implements UserDao {

	private static final long serialVersionUID = 4104435792475714435L;
	
	@Override
    public AppUser findById (Long id) {
        AppUser user = super.findById(id);
        if(user!=null){
            Hibernate.initialize(user.getUserRoles());
            Hibernate.initialize(user.getUserDirectories());
        }
        return user;
    }

    @Override
    public AppUser findByName (String name)
    {
    	CriteriaQuery<AppUser> query = this.createCriteria();
    	CriteriaBuilder builder = getSession().getCriteriaBuilder();
    	Root<AppUser> root = query.from(AppUser.class);
    	
    	query.select(root).where(builder.equal(root.get("name"), name));
    	
    	Query<AppUser> q = getSession().createQuery(query);
    	AppUser user = q.getSingleResult();
        if(user!=null){
            Hibernate.initialize(user.getUserRoles());
            Hibernate.initialize(user.getUserDirectories());
        }
        return user;
    }
    
    @Override
	public void save(AppUser user) {
		super.persist(user);
	}

	@Override
	public List<AppUser> findAll() {
		CriteriaQuery<AppUser> query = this.createCriteria();
		Root<AppUser> root = query.from(AppUser.class);
        query.select(root);
        Query<AppUser> q = getSession().createQuery(query);
        
        if (q!=null)
        {
        	List<AppUser> list = q.getResultList();
        	if (list != null)
        	{
        		for (AppUser u : list)
        		{
        			Hibernate.initialize(u.getUserRoles());
        			Hibernate.initialize(u.getUserDirectories());
        		}
        		return list;
        	}
        }
        return null;
	}

	@Override
	public void remove(AppUser user) {
		super.delete(user);
	}
}
