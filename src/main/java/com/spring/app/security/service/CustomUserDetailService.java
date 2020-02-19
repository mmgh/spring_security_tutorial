package com.spring.app.security.service;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService{

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.info("loadUserByUsername username : " + username);
		
		if( !(username.equals("admin") || username.equals("dba")) ) {
			throw new UsernameNotFoundException(username + " not found");
		}
		
		
		// creating dummy user details, should do JDBC opertion
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
