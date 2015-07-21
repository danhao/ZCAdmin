package com.zc.common.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zc.common.dao.SecRightDAO;
import com.zc.common.dao.SecUserDAO;
import com.zc.common.dao.SecUserRoleDAO;
import com.zc.common.model.SecRight;
import com.zc.common.model.SecUser;
import com.zc.common.model.SecUserExample;

@Service("userService")
public class UserService implements BaseService<SecUserExample, SecUser> {

	@Resource
	private SecUserDAO secUserDAO;
	@Resource
	private SecRightDAO secRightDAO;
	@Resource
	private SecUserRoleDAO secUserRoleDAO;

	public SecUserDAO getSecUserDAO() {
		return secUserDAO;
	}

	public void setSecUserDAO(SecUserDAO secUserDAO) {
		this.secUserDAO = secUserDAO;
	}

	public SecRightDAO getSecRightDAO() {
		return secRightDAO;
	}

	public void setSecRightDAO(SecRightDAO secRightDAO) {
		this.secRightDAO = secRightDAO;
	}

	public SecUserRoleDAO getSecUserRoleDAO() {
		return secUserRoleDAO;
	}

	public void setSecUserRoleDAO(SecUserRoleDAO secUserRoleDAO) {
		this.secUserRoleDAO = secUserRoleDAO;
	}

	public List<SecUser> paginate(SecUserExample example) {
		return getSecUserDAO().selectByExample(example);
	}

	public void saveOrUpdate(SecUser user) {
		if (user.getId() == null) {
			user.setCreatedAt(new Date());
			getSecUserDAO().insert(user);
		} else {
			getSecUserDAO().updateByPrimaryKey(user);
		}
	}

	public void delete(int id) {
		getSecUserDAO().deleteByPrimaryKey(id);
	}

	public void delete(SecUserExample example) {
		getSecUserDAO().deleteByExample(example);
	}

	public boolean isExists(SecUserExample example) {
		List<SecUser> users = getSecUserDAO().selectByExample(example);
		return (users != null && !users.isEmpty()) ? true : false;
	}

	public SecUser getUserByLoginname(final String userName) {
		SecUserExample example = new SecUserExample();
		example.createCriteria().andNameEqualTo(userName);
		List<SecUser> users = getSecUserDAO().selectByExample(example);
		return (users != null && !users.isEmpty()) ? users.get(0) : null;
	}

	public int getTotal(SecUserExample example) {
		return getSecUserDAO().countByExample(example);
	}

	public List<SecRight> getRightsByUser(SecUser user) {
		return getSecRightDAO().getUserRights(user.getName());
	}
}
