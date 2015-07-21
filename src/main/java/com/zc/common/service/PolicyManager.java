package com.zc.common.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zc.common.model.SecRight;
import com.zc.common.model.SecUser;

public class PolicyManager implements UserDetailsService, Serializable {
	private static final long serialVersionUID = 412963014832499452L;
	private transient UserService userService;
	private UserDetails userDetails;

	public UserDetails loadUserByUsername(String userId) {
		SecUser secUser = null;
		secUser = getUserByLoginname(userId);
		if (secUser == null) {
			throw new UsernameNotFoundException("Invalid User");
		}
		Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthority(secUser);
		userDetails = new User(secUser.getName(), secUser.getPassword(), secUser.getEnabled(), secUser.getAccountnonexpired(), secUser.getAccountnonlocked(), secUser.getCredentialsnonexpired(), grantedAuthorities);
		return userDetails;

	}

	public SecUser getUserByLoginname(final String userName) {
		return getUserService().getUserByLoginname(userName);
	}

	private Collection<GrantedAuthority> getGrantedAuthority(SecUser user) {
		Collection<SecRight> rights = getUserService().getRightsByUser(user);
		List<GrantedAuthority> rechteGrantedAuthorities = new ArrayList<GrantedAuthority>();
		for (SecRight right : rights) {
			rechteGrantedAuthorities.add(new GrantedAuthorityImpl(right.getName()));
		}
		return rechteGrantedAuthorities;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
