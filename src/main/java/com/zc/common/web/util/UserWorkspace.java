package com.zc.common.web.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

public class UserWorkspace implements Serializable, DisposableBean {
	private static final long serialVersionUID = 1423595533999341219L;
	final static Logger logger = LoggerFactory.getLogger(UserWorkspace.class);

	private Set<String> grantedAuthoritySet = null;

	private static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public UserWorkspace() {
		Window.setDefaultActionOnShow("");
	}

	public void doLogout() {
		destroy();
		Executions.sendRedirect("/j_spring_logout");

	}

	private Set<String> getGrantedAuthoritySet() {
		if (grantedAuthoritySet == null) {
			Collection<GrantedAuthority> list = getAuthentication().getAuthorities();
			grantedAuthoritySet = new HashSet<String>(list.size());
			for (GrantedAuthority grantedAuthority : list) {
				grantedAuthoritySet.add(grantedAuthority.getAuthority());
			}
			logger.debug("用户权限:" + grantedAuthoritySet);
		}
		return grantedAuthoritySet;
	}

	public boolean isAllowed(String rightName) {
		return getGrantedAuthoritySet().contains(rightName.trim());
	}

	public void destroy() {
		grantedAuthoritySet = null;
		SecurityContextHolder.clearContext();
	}
}
