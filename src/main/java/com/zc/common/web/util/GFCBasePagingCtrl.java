package com.zc.common.web.util;

import javax.annotation.Resource;


public abstract class GFCBasePagingCtrl extends GFCBaseCtrl {
	private static final long serialVersionUID = -6781425661675058538L;

	@Resource
	private PagingWrap pagingWrap;

	public PagingWrap getPagingWrap() {
		return pagingWrap;
	}

	public void setPagingWrap(PagingWrap pagingWrap) {
		this.pagingWrap = pagingWrap;
	}

}
