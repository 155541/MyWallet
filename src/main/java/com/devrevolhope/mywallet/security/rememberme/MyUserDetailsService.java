package main.java.com.devrevolhope.mywallet.security.rememberme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.java.com.devrevolhope.mywallet.model.AppUser;
import main.java.com.devrevolhope.mywallet.model.AppUserRole;
import main.java.com.devrevolhope.mywallet.service.UserService;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		if (username != null && !username.isEmpty()) 
		{
			AppUser user = userService.findByName(username);
			if(user==null)
			{
				throw new UsernameNotFoundException("Username not found");
			}
			return new User(user.getName(), user.getPassword(),true, true, true, true, getGrantedAuthorities(user));
		}
		throw new UsernameNotFoundException("Username not found, id = _"+ username +"_");
	}


	private List<GrantedAuthority> getGrantedAuthorities(AppUser user){

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for(AppUserRole role : user.getUserRoles())
		{
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getType()));
		}

		return authorities;
	}

}