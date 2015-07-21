package com.zc.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zc.common.dao.SecRoleDAO;
import com.zc.common.model.SecRole;
import com.zc.common.model.SecRoleExample;

@Service
public class RoleService implements BaseService<SecRoleExample, SecRole> {

	@Resource
	private SecRoleDAO secRoleDAO;

	public SecRoleDAO getSecRoleDAO() {
		return secRoleDAO;
	}

	public void setSecRoleDAO(SecRoleDAO secRoleDAO) {
		this.secRoleDAO = secRoleDAO;
	}

	public List<SecRole> paginate(SecRoleExample example) {
		return getSecRoleDAO().selectByExample(example);
	}

	public void saveOrUpdate(SecRole role) {
		if (role.getId() == null) {
			getSecRoleDAO().insert(role);
		} else {
			getSecRoleDAO().updateByPrimaryKey(role);
		}
	}

	public void delete(int id) {
		getSecRoleDAO().deleteByPrimaryKey(id);
	}

	public void delete(SecRoleExample example) {
		getSecRoleDAO().deleteByExample(example);
	}

	public boolean isExists(SecRoleExample example) {
		List<SecRole> roles = getSecRoleDAO().selectByExample(example);
		return (roles != null && !roles.isEmpty()) ? true : false;
	}

	public int getTotal(SecRoleExample example) {
		return getSecRoleDAO().countByExample(example);
	}

	public boolean isNew(SecRole role) {
		return role.getId() == null || role.getId().intValue() == 0;
	}
}
