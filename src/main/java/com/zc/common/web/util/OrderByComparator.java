package com.zc.common.web.util;

import java.io.Serializable;
import java.util.Comparator;

public class OrderByComparator implements Comparator<Object>, Serializable {
	private static final long serialVersionUID = 98282822426950891L;
	private String orderByClause;

	public OrderByComparator(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public int compare(Object o1, Object o2) {
		return 0;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}
}
