package com.zc.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zc.common.dao.SecRightDAO;
import com.zc.common.model.SecRight;
import com.zc.common.model.SecRightExample;

@Service
public class RightService implements BaseService<SecRightExample,SecRight> {
	private static final long serialVersionUID = -4327281549800803704L;
	@Resource
	private SecRightDAO secRightDAO;

	public SecRightDAO getSecRightDAO() {
		return secRightDAO;
	}

	public void setSecRightDAO(SecRightDAO secRightDAO) {
		this.secRightDAO = secRightDAO;
	}

	public List<SecRight> paginate(SecRightExample example) {
		return getSecRightDAO().selectByExample(example);
	}

	public void saveOrUpdate(SecRight role) {
		if (role.getId() == null) {
			getSecRightDAO().insert(role);
		} else {
			getSecRightDAO().updateByPrimaryKey(role);
		}
	}

	public void delete(int id) {
		getSecRightDAO().deleteByPrimaryKey(id);
	}

	public void delete(SecRightExample example) {
		getSecRightDAO().deleteByExample(example);
	}

	public boolean isExists(SecRightExample example) {
		List<SecRight> roles = getSecRightDAO().selectByExample(example);
		return (roles != null && !roles.isEmpty()) ? true : false;
	}

	public int getTotal(SecRightExample example) {
		return getSecRightDAO().countByExample(example);
	}
}
