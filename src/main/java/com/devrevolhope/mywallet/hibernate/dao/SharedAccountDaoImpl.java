package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import main.java.com.devrevolhope.mywallet.model.AppUser;
import main.java.com.devrevolhope.mywallet.model.SharedAccount;

@Repository("sharedAccountDao")
public class SharedAccountDaoImpl extends AbstractDao<Long, SharedAccount> implements SharedAccountDao{
	
	private static final long serialVersionUID = -5354916697433636888L;

	@Override
	public SharedAccount findById(long id) {
		return super.findById(id);
	}
	
	@Override
	public Map<Long,List<SharedAccount>> findAllSharings(long userId) {
		
		CriteriaQuery<SharedAccount> query = super.createCriteria();
        Root<SharedAccount> root = query.from(SharedAccount.class);
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        query.select(root).where(builder.or(builder.equal(root.get("accountOwner"), userId),
                                            builder.equal(root.get("userShared"), userId)));
        
        Query<SharedAccount> q = getSession().createQuery(query);
        List<SharedAccount> sharedAccounts = q.getResultList();
        
        Map<Long,List<SharedAccount>> map = new HashMap<>();
        AppUser user;
        long id;
        for (SharedAccount sa : sharedAccounts)
        {
            Hibernate.initialize(sa.getAccountOwner());
            Hibernate.initialize(sa.getUserShared());
            Hibernate.initialize(sa.getAccountShared());
            
            if (sa.getAccountOwner().getId() == userId)
            {
                user = sa.getUserShared();
            }
            else
            {
                user = sa.getAccountOwner();
            }
            
            id = user.getId();
            
            if (!map.containsKey(id))
            {
                map.put(id, new ArrayList<>());
            }
            map.get(id).add(sa);    
        }
        return map;
	}

	@Override
	public List<SharedAccount> findSharedFrom(long id, long userId) {
		
		CriteriaQuery<SharedAccount> query = super.createCriteria();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
        Root<SharedAccount> root = query.from(SharedAccount.class);
        query.select(root)
                .where(builder.or(
                            builder.and(builder.equal(root.get("accountOwner"),userId),
                                        builder.equal(root.get("userShared"),id)),
                            builder.and(builder.equal(root.get("accountOwner"),id),
                                        builder.equal(root.get("userShared"),userId))));
                                        
        Query<SharedAccount> q = getSession().createQuery(query);
    	return q.getResultList();
	}

	@Override
	public void save(SharedAccount sharedAccount) {
		super.persist(sharedAccount);
	}

	@Override
	public void remove(SharedAccount sharedAccount) {
		super.delete(sharedAccount);
	}

	@Override
	public boolean isShared(long idAccount, long userId) {
		
		CriteriaQuery<SharedAccount> query = super.createCriteria();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
        Root<SharedAccount> root = query.from(SharedAccount.class);
        query.select(root).where(builder.and(builder.equal(root.get("accountOwner"),userId),
                                        	 builder.equal(root.get("accountShared"),idAccount)));
                                        
        Query<SharedAccount> q = getSession().createQuery(query);
    	return !q.getResultList().isEmpty();
	}
}
