package com.zc.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Service;

import com.zc.common.dao.SecRoleRightDAO;
import com.zc.common.dao.SecUserRoleDAO;
import com.zc.common.model.SecRight;
import com.zc.common.model.SecRole;
import com.zc.common.model.SecRoleRightExample;
import com.zc.common.model.SecRoleRightKey;
import com.zc.common.model.SecUser;
import com.zc.common.model.SecUserRoleExample;
import com.zc.common.model.SecUserRoleKey;

@Service
public class SecurityService {

	@Resource
	private SecUserRoleDAO secUserRoleDAO;

	@Resource
	private SecRoleRightDAO secRoleRightDAO;

	public SecUserRoleDAO getSecUserRoleDAO() {
		return secUserRoleDAO;
	}

	public void setSecUserRoleDAO(SecUserRoleDAO secUserRoleDAO) {
		this.secUserRoleDAO = secUserRoleDAO;
	}

	public SecRoleRightDAO getSecRoleRightDAO() {
		return secRoleRightDAO;
	}

	public void setSecRoleRightDAO(SecRoleRightDAO secRoleRightDAO) {
		this.secRoleRightDAO = secRoleRightDAO;
	}

	/**
	 * 某用户是否具有某角色
	 * 
	 * @param user
	 * @param role
	 * @return
	 */
	public boolean isUserInRole(SecUser user, SecRole role) {
		SecUserRoleExample example = new SecUserRoleExample();
		example.createCriteria().andUserIdEqualTo(user.getId()).andRoleIdEqualTo(role.getId());
		List<SecUserRoleKey> results = getSecUserRoleDAO().selectByExample(example);
		return (results != null && !results.isEmpty()) ? true : false;
	}

	public SecUserRoleKey getUserroleByUserAndRole(SecUser user, SecRole role) {
		SecUserRoleExample example = new SecUserRoleExample();
		example.createCriteria().andUserIdEqualTo(user.getId()).andRoleIdEqualTo(role.getId());
		List<SecUserRoleKey> results = getSecUserRoleDAO().selectByExample(example);
		return DataAccessUtils.uniqueResult(results);
	}

	public void saveOrUpdateUserRole(SecUserRoleKey userRole) {
		getSecUserRoleDAO().insertSelective(userRole);
	}

	public void saveOrUpdateRoleRight(SecRoleRightKey roleRight) {
		getSecRoleRightDAO().insertSelective(roleRight);
	}

	public void deleteUserRole(SecUserRoleKey userRole) {
		getSecUserRoleDAO().deleteByPrimaryKey(userRole);
	}

	public void deleteRoleRight(SecRoleRightKey roleRight) {
		getSecRoleRightDAO().deleteByPrimaryKey(roleRight);
	}

	public boolean isRoleInRight(SecRole role, SecRight right) {
		SecRoleRightExample example = new SecRoleRightExample();
		example.createCriteria().andRoleIdEqualTo(role.getId()).andRightIdEqualTo(right.getId());
		List<SecRoleRightKey> results = getSecRoleRightDAO().selectByExample(example);
		return (results != null && !results.isEmpty()) ? true : false;
	}

	public SecRoleRightKey getRolerightByRoleAndRight(SecRole role, SecRight right) {
		SecRoleRightExample example = new SecRoleRightExample();
		example.createCriteria().andRoleIdEqualTo(role.getId()).andRightIdEqualTo(right.getId());
		List<SecRoleRightKey> results = getSecRoleRightDAO().selectByExample(example);
		return DataAccessUtils.uniqueResult(results);
	}

	public void deleteUserRole(SecUserRoleExample example) {
		getSecUserRoleDAO().deleteByExample(example);
	}

	public void deleteRoleRight(SecRoleRightExample example) {
		getSecRoleRightDAO().deleteByExample(example);
	}

}
