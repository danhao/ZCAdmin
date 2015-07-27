package com.zc.common.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class BaseModel implements Serializable {
	private static final long serialVersionUID = 8269677744832303045L;

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
