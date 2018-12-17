package com.spring.app.security.dao;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailServiceDAO implements UserDetailsService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		
		logger.info("loadUserByUsername username : " + username);
		
		if(!username.equals("admin")) {
			throw new UsernameNotFoundException(username + " not found");
		}
		
		//creating dummy user details, should do JDBC opertion
		return new UserDetails() {
			
			private static final long serialVersionUID = 1181386790787910601L;

			@Override
			public boolean isEnabled() {
				return true;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				return true;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				return true;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				return true;
			}
			
			@Override
			public String getUsername() {
				return username;
			}
			
			@Override
			public String getPassword() {
				return "admin123";
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				
				List<SimpleGrantedAuthority> auth = new java.util.ArrayList<SimpleGrantedAuthority>();
				auth.add(new SimpleGrantedAuthority("Admin"));
				return auth;
			}
			
		};
		
		
		
		
		
	}

}
