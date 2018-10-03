package main.java.com.devrevolhope.mywallet.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import main.java.com.devrevolhope.mywallet.model.AppUserRole;
import main.java.com.devrevolhope.mywallet.service.UserRoleService;

@Component
public class RoleConverter implements Converter<Object, AppUserRole>{

	@Autowired
	UserRoleService roleService; 
	
	@Override
	public AppUserRole convert(Object source) {
		Long id = Long.parseLong((String)source);
		AppUserRole role = roleService.findById(id);
		return role;
	}
}
