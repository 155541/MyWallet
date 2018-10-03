package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import main.java.com.devrevolhope.mywallet.model.AppUserRole;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends AbstractDao<Long, AppUserRole> implements UserRoleDao{

	private static final long serialVersionUID = -6003395067651999563L;

	@Override
	public List<AppUserRole> findAll() {
		CriteriaQuery<AppUserRole> query = this.createCriteria();
		Root<AppUserRole> root = query.from(AppUserRole.class);
        query.select(root);
        Query<AppUserRole> q = getSession().createQuery(query);
        return q.getResultList();
	}

	@Override
	public AppUserRole findById(Long id) {
		AppUserRole role = super.findById(id);
		if (role != null)
		{
			Hibernate.initialize(role.getUsersAssigned());
		}
		return role;
	}
	
	@Override
	public AppUserRole findByType(String type) {
		CriteriaQuery<AppUserRole> query = this.createCriteria();
    	CriteriaBuilder builder = getSession().getCriteriaBuilder();
    	
    	Root<AppUserRole> root = query.from(AppUserRole.class);
    	query.select(root).where(builder.equal(root.get("TYPE"), type));
    	Query<AppUserRole> q = getSession().createQuery(query);
    	AppUserRole role = q.getSingleResult();
        if(role!=null){
            Hibernate.initialize(role.getUsersAssigned());
        }
        return role;
	}

	@Override
	public void save(AppUserRole role) {
		super.persist(role);
	}

	@Override
	public void remove(AppUserRole role) {
		super.delete(role);
	}
}
