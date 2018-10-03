package main.java.com.devrevolhope.mywallet.hibernate.dao;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import main.java.com.devrevolhope.mywallet.security.rememberme.PersistentLogin;

@Repository("tokenRepositoryDao")
@Transactional
public class HibernateTokenRepository extends AbstractDao<String, PersistentLogin> implements PersistentTokenRepository {

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		PersistentLogin persistentLogin = new PersistentLogin();
		persistentLogin.setUsername(token.getUsername());
		persistentLogin.setSeries(token.getSeries());
		persistentLogin.setToken(token.getTokenValue());
		persistentLogin.setLast_used(token.getDate());
		persist(persistentLogin);

	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		try {
			CriteriaQuery<PersistentLogin> query = super.createCriteria();
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			
			Root<PersistentLogin> root = query.from(PersistentLogin.class);
	    	query.select(root).where(builder.equal(root.get("SERIES"), seriesId));
	    	Query<PersistentLogin> q = getSession().createQuery(query);
	    	PersistentLogin persistentLogin = q.getSingleResult();
			
			return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
					persistentLogin.getToken(), persistentLogin.getLast_used());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void removeUserTokens(String username) {

		CriteriaQuery<PersistentLogin> query = super.createCriteria();
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		
		Root<PersistentLogin> root = query.from(PersistentLogin.class);
    	query.select(root).where(builder.equal(root.get("USERNAME"), username));
    	Query<PersistentLogin> q = getSession().createQuery(query);
    	PersistentLogin persistentLogin = q.getSingleResult();

		if (persistentLogin != null) {
			super.delete(persistentLogin);
		}
	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
		PersistentLogin persistentLogin = super.findById(seriesId);
		persistentLogin.setToken(tokenValue);
		persistentLogin.setLast_used(lastUsed);
		update(persistentLogin);
	}
}
