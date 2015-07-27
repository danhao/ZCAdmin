package com.zc.common.service;

import java.util.List;

import com.zc.common.model.BaseExample;
import com.zc.common.model.BaseModel;

public interface BaseService<T extends BaseExample, E extends BaseModel> {
	public int getTotal(T example);

	public List<E> paginate(T example);
}
