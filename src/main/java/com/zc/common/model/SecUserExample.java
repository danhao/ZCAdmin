package com.zc.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecUserExample extends BaseExample{

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	public SecUserExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	protected SecUserExample(SecUserExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table sec_user
	 * @ibatorgenerated  Thu Apr 08 12:29:43 CST 2010
	 */
	public static class Criteria {
		protected List<String> criteriaWithoutValue;
		protected List<Map<String, Object>> criteriaWithSingleValue;
		protected List<Map<String, Object>> criteriaWithListValue;
		protected List<Map<String, Object>> criteriaWithBetweenValue;

		protected Criteria() {
			super();
			criteriaWithoutValue = new ArrayList<String>();
			criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
			criteriaWithListValue = new ArrayList<Map<String, Object>>();
			criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
		}

		public boolean isValid() {
			return criteriaWithoutValue.size() > 0 || criteriaWithSingleValue.size() > 0 || criteriaWithListValue.size() > 0 || criteriaWithBetweenValue.size() > 0;
		}

		public List<String> getCriteriaWithoutValue() {
			return criteriaWithoutValue;
		}

		public List<Map<String, Object>> getCriteriaWithSingleValue() {
			return criteriaWithSingleValue;
		}

		public List<Map<String, Object>> getCriteriaWithListValue() {
			return criteriaWithListValue;
		}

		public List<Map<String, Object>> getCriteriaWithBetweenValue() {
			return criteriaWithBetweenValue;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteriaWithoutValue.add(condition);
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition, List<? extends Object> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property + " cannot be null or empty");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			List<Object> list = new ArrayList<Object>();
			list.add(value1);
			list.add(value2);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("values", list);
			criteriaWithBetweenValue.add(map);
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("id =", value, "id");
			return this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("id <>", value, "id");
			return this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("id >", value, "id");
			return this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("id >=", value, "id");
			return this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("id <", value, "id");
			return this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("id <=", value, "id");
			return this;
		}

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("id in", values, "id");
			return this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
			addCriterion("id not in", values, "id");
			return this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("id between", value1, value2, "id");
			return this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("id not between", value1, value2, "id");
			return this;
		}

		public Criteria andNameIsNull() {
			addCriterion("name is null");
			return this;
		}

		public Criteria andNameIsNotNull() {
			addCriterion("name is not null");
			return this;
		}

		public Criteria andNameEqualTo(String value) {
			addCriterion("name =", value, "name");
			return this;
		}

		public Criteria andNameNotEqualTo(String value) {
			addCriterion("name <>", value, "name");
			return this;
		}

		public Criteria andNameGreaterThan(String value) {
			addCriterion("name >", value, "name");
			return this;
		}

		public Criteria andNameGreaterThanOrEqualTo(String value) {
			addCriterion("name >=", value, "name");
			return this;
		}

		public Criteria andNameLessThan(String value) {
			addCriterion("name <", value, "name");
			return this;
		}

		public Criteria andNameLessThanOrEqualTo(String value) {
			addCriterion("name <=", value, "name");
			return this;
		}

		public Criteria andNameLike(String value) {
			addCriterion("name like", value, "name");
			return this;
		}

		public Criteria andNameNotLike(String value) {
			addCriterion("name not like", value, "name");
			return this;
		}

		public Criteria andNameIn(List<String> values) {
			addCriterion("name in", values, "name");
			return this;
		}

		public Criteria andNameNotIn(List<String> values) {
			addCriterion("name not in", values, "name");
			return this;
		}

		public Criteria andNameBetween(String value1, String value2) {
			addCriterion("name between", value1, value2, "name");
			return this;
		}

		public Criteria andNameNotBetween(String value1, String value2) {
			addCriterion("name not between", value1, value2, "name");
			return this;
		}

		public Criteria andPasswordIsNull() {
			addCriterion("password is null");
			return this;
		}

		public Criteria andPasswordIsNotNull() {
			addCriterion("password is not null");
			return this;
		}

		public Criteria andPasswordEqualTo(String value) {
			addCriterion("password =", value, "password");
			return this;
		}

		public Criteria andPasswordNotEqualTo(String value) {
			addCriterion("password <>", value, "password");
			return this;
		}

		public Criteria andPasswordGreaterThan(String value) {
			addCriterion("password >", value, "password");
			return this;
		}

		public Criteria andPasswordGreaterThanOrEqualTo(String value) {
			addCriterion("password >=", value, "password");
			return this;
		}

		public Criteria andPasswordLessThan(String value) {
			addCriterion("password <", value, "password");
			return this;
		}

		public Criteria andPasswordLessThanOrEqualTo(String value) {
			addCriterion("password <=", value, "password");
			return this;
		}

		public Criteria andPasswordLike(String value) {
			addCriterion("password like", value, "password");
			return this;
		}

		public Criteria andPasswordNotLike(String value) {
			addCriterion("password not like", value, "password");
			return this;
		}

		public Criteria andPasswordIn(List<String> values) {
			addCriterion("password in", values, "password");
			return this;
		}

		public Criteria andPasswordNotIn(List<String> values) {
			addCriterion("password not in", values, "password");
			return this;
		}

		public Criteria andPasswordBetween(String value1, String value2) {
			addCriterion("password between", value1, value2, "password");
			return this;
		}

		public Criteria andPasswordNotBetween(String value1, String value2) {
			addCriterion("password not between", value1, value2, "password");
			return this;
		}

		public Criteria andEnabledIsNull() {
			addCriterion("enabled is null");
			return this;
		}

		public Criteria andEnabledIsNotNull() {
			addCriterion("enabled is not null");
			return this;
		}

		public Criteria andEnabledEqualTo(Boolean value) {
			addCriterion("enabled =", value, "enabled");
			return this;
		}

		public Criteria andEnabledNotEqualTo(Boolean value) {
			addCriterion("enabled <>", value, "enabled");
			return this;
		}

		public Criteria andEnabledGreaterThan(Boolean value) {
			addCriterion("enabled >", value, "enabled");
			return this;
		}

		public Criteria andEnabledGreaterThanOrEqualTo(Boolean value) {
			addCriterion("enabled >=", value, "enabled");
			return this;
		}

		public Criteria andEnabledLessThan(Boolean value) {
			addCriterion("enabled <", value, "enabled");
			return this;
		}

		public Criteria andEnabledLessThanOrEqualTo(Boolean value) {
			addCriterion("enabled <=", value, "enabled");
			return this;
		}

		public Criteria andEnabledIn(List<Boolean> values) {
			addCriterion("enabled in", values, "enabled");
			return this;
		}

		public Criteria andEnabledNotIn(List<Boolean> values) {
			addCriterion("enabled not in", values, "enabled");
			return this;
		}

		public Criteria andEnabledBetween(Boolean value1, Boolean value2) {
			addCriterion("enabled between", value1, value2, "enabled");
			return this;
		}

		public Criteria andEnabledNotBetween(Boolean value1, Boolean value2) {
			addCriterion("enabled not between", value1, value2, "enabled");
			return this;
		}

		public Criteria andAccountnonexpiredIsNull() {
			addCriterion("accountnonexpired is null");
			return this;
		}

		public Criteria andAccountnonexpiredIsNotNull() {
			addCriterion("accountnonexpired is not null");
			return this;
		}

		public Criteria andAccountnonexpiredEqualTo(Boolean value) {
			addCriterion("accountnonexpired =", value, "accountnonexpired");
			return this;
		}

		public Criteria andAccountnonexpiredNotEqualTo(Boolean value) {
			addCriterion("accountnonexpired <>", value, "accountnonexpired");
			return this;
		}

		public Criteria andAccountnonexpiredGreaterThan(Boolean value) {
			addCriterion("accountnonexpired >", value, "accountnonexpired");
			return this;
		}

		public Criteria andAccountnonexpiredGreaterThanOrEqualTo(Boolean value) {
			addCriterion("accountnonexpired >=", value, "accountnonexpired");
			return this;
		}

		public Criteria andAccountnonexpiredLessThan(Boolean value) {
			addCriterion("accountnonexpired <", value, "accountnonexpired");
			return this;
		}

		public Criteria andAccountnonexpiredLessThanOrEqualTo(Boolean value) {
			addCriterion("accountnonexpired <=", value, "accountnonexpired");
			return this;
		}

		public Criteria andAccountnonexpiredIn(List<Boolean> values) {
			addCriterion("accountnonexpired in", values, "accountnonexpired");
			return this;
		}

		public Criteria andAccountnonexpiredNotIn(List<Boolean> values) {
			addCriterion("accountnonexpired not in", values, "accountnonexpired");
			return this;
		}

		public Criteria andAccountnonexpiredBetween(Boolean value1, Boolean value2) {
			addCriterion("accountnonexpired between", value1, value2, "accountnonexpired");
			return this;
		}

		public Criteria andAccountnonexpiredNotBetween(Boolean value1, Boolean value2) {
			addCriterion("accountnonexpired not between", value1, value2, "accountnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredIsNull() {
			addCriterion("credentialsnonexpired is null");
			return this;
		}

		public Criteria andCredentialsnonexpiredIsNotNull() {
			addCriterion("credentialsnonexpired is not null");
			return this;
		}

		public Criteria andCredentialsnonexpiredEqualTo(Boolean value) {
			addCriterion("credentialsnonexpired =", value, "credentialsnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredNotEqualTo(Boolean value) {
			addCriterion("credentialsnonexpired <>", value, "credentialsnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredGreaterThan(Boolean value) {
			addCriterion("credentialsnonexpired >", value, "credentialsnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredGreaterThanOrEqualTo(Boolean value) {
			addCriterion("credentialsnonexpired >=", value, "credentialsnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredLessThan(Boolean value) {
			addCriterion("credentialsnonexpired <", value, "credentialsnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredLessThanOrEqualTo(Boolean value) {
			addCriterion("credentialsnonexpired <=", value, "credentialsnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredIn(List<Boolean> values) {
			addCriterion("credentialsnonexpired in", values, "credentialsnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredNotIn(List<Boolean> values) {
			addCriterion("credentialsnonexpired not in", values, "credentialsnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredBetween(Boolean value1, Boolean value2) {
			addCriterion("credentialsnonexpired between", value1, value2, "credentialsnonexpired");
			return this;
		}

		public Criteria andCredentialsnonexpiredNotBetween(Boolean value1, Boolean value2) {
			addCriterion("credentialsnonexpired not between", value1, value2, "credentialsnonexpired");
			return this;
		}

		public Criteria andAccountnonlockedIsNull() {
			addCriterion("accountnonlocked is null");
			return this;
		}

		public Criteria andAccountnonlockedIsNotNull() {
			addCriterion("accountnonlocked is not null");
			return this;
		}

		public Criteria andAccountnonlockedEqualTo(Boolean value) {
			addCriterion("accountnonlocked =", value, "accountnonlocked");
			return this;
		}

		public Criteria andAccountnonlockedNotEqualTo(Boolean value) {
			addCriterion("accountnonlocked <>", value, "accountnonlocked");
			return this;
		}

		public Criteria andAccountnonlockedGreaterThan(Boolean value) {
			addCriterion("accountnonlocked >", value, "accountnonlocked");
			return this;
		}

		public Criteria andAccountnonlockedGreaterThanOrEqualTo(Boolean value) {
			addCriterion("accountnonlocked >=", value, "accountnonlocked");
			return this;
		}

		public Criteria andAccountnonlockedLessThan(Boolean value) {
			addCriterion("accountnonlocked <", value, "accountnonlocked");
			return this;
		}

		public Criteria andAccountnonlockedLessThanOrEqualTo(Boolean value) {
			addCriterion("accountnonlocked <=", value, "accountnonlocked");
			return this;
		}

		public Criteria andAccountnonlockedIn(List<Boolean> values) {
			addCriterion("accountnonlocked in", values, "accountnonlocked");
			return this;
		}

		public Criteria andAccountnonlockedNotIn(List<Boolean> values) {
			addCriterion("accountnonlocked not in", values, "accountnonlocked");
			return this;
		}

		public Criteria andAccountnonlockedBetween(Boolean value1, Boolean value2) {
			addCriterion("accountnonlocked between", value1, value2, "accountnonlocked");
			return this;
		}

		public Criteria andAccountnonlockedNotBetween(Boolean value1, Boolean value2) {
			addCriterion("accountnonlocked not between", value1, value2, "accountnonlocked");
			return this;
		}

		public Criteria andDescnIsNull() {
			addCriterion("descn is null");
			return this;
		}

		public Criteria andDescnIsNotNull() {
			addCriterion("descn is not null");
			return this;
		}

		public Criteria andDescnEqualTo(String value) {
			addCriterion("descn =", value, "descn");
			return this;
		}

		public Criteria andDescnNotEqualTo(String value) {
			addCriterion("descn <>", value, "descn");
			return this;
		}

		public Criteria andDescnGreaterThan(String value) {
			addCriterion("descn >", value, "descn");
			return this;
		}

		public Criteria andDescnGreaterThanOrEqualTo(String value) {
			addCriterion("descn >=", value, "descn");
			return this;
		}

		public Criteria andDescnLessThan(String value) {
			addCriterion("descn <", value, "descn");
			return this;
		}

		public Criteria andDescnLessThanOrEqualTo(String value) {
			addCriterion("descn <=", value, "descn");
			return this;
		}

		public Criteria andDescnLike(String value) {
			addCriterion("descn like", value, "descn");
			return this;
		}

		public Criteria andDescnNotLike(String value) {
			addCriterion("descn not like", value, "descn");
			return this;
		}

		public Criteria andDescnIn(List<String> values) {
			addCriterion("descn in", values, "descn");
			return this;
		}

		public Criteria andDescnNotIn(List<String> values) {
			addCriterion("descn not in", values, "descn");
			return this;
		}

		public Criteria andDescnBetween(String value1, String value2) {
			addCriterion("descn between", value1, value2, "descn");
			return this;
		}

		public Criteria andDescnNotBetween(String value1, String value2) {
			addCriterion("descn not between", value1, value2, "descn");
			return this;
		}

		public Criteria andCreatedAtIsNull() {
			addCriterion("created_at is null");
			return this;
		}

		public Criteria andCreatedAtIsNotNull() {
			addCriterion("created_at is not null");
			return this;
		}

		public Criteria andCreatedAtEqualTo(Date value) {
			addCriterion("created_at =", value, "createdAt");
			return this;
		}

		public Criteria andCreatedAtNotEqualTo(Date value) {
			addCriterion("created_at <>", value, "createdAt");
			return this;
		}

		public Criteria andCreatedAtGreaterThan(Date value) {
			addCriterion("created_at >", value, "createdAt");
			return this;
		}

		public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
			addCriterion("created_at >=", value, "createdAt");
			return this;
		}

		public Criteria andCreatedAtLessThan(Date value) {
			addCriterion("created_at <", value, "createdAt");
			return this;
		}

		public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
			addCriterion("created_at <=", value, "createdAt");
			return this;
		}

		public Criteria andCreatedAtIn(List<Date> values) {
			addCriterion("created_at in", values, "createdAt");
			return this;
		}

		public Criteria andCreatedAtNotIn(List<Date> values) {
			addCriterion("created_at not in", values, "createdAt");
			return this;
		}

		public Criteria andCreatedAtBetween(Date value1, Date value2) {
			addCriterion("created_at between", value1, value2, "createdAt");
			return this;
		}

		public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
			addCriterion("created_at not between", value1, value2, "createdAt");
			return this;
		}
	}
}