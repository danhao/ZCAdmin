package com.zc.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecRightExample extends BaseExample{

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	public SecRightExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	protected SecRightExample(SecRightExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table sec_right
	 * @ibatorgenerated  Wed Apr 21 13:14:51 CST 2010
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

		public Criteria andTypeIsNull() {
			addCriterion("type is null");
			return this;
		}

		public Criteria andTypeIsNotNull() {
			addCriterion("type is not null");
			return this;
		}

		public Criteria andTypeEqualTo(Integer value) {
			addCriterion("type =", value, "type");
			return this;
		}

		public Criteria andTypeNotEqualTo(Integer value) {
			addCriterion("type <>", value, "type");
			return this;
		}

		public Criteria andTypeGreaterThan(Integer value) {
			addCriterion("type >", value, "type");
			return this;
		}

		public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("type >=", value, "type");
			return this;
		}

		public Criteria andTypeLessThan(Integer value) {
			addCriterion("type <", value, "type");
			return this;
		}

		public Criteria andTypeLessThanOrEqualTo(Integer value) {
			addCriterion("type <=", value, "type");
			return this;
		}

		public Criteria andTypeIn(List<Integer> values) {
			addCriterion("type in", values, "type");
			return this;
		}

		public Criteria andTypeNotIn(List<Integer> values) {
			addCriterion("type not in", values, "type");
			return this;
		}

		public Criteria andTypeBetween(Integer value1, Integer value2) {
			addCriterion("type between", value1, value2, "type");
			return this;
		}

		public Criteria andTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("type not between", value1, value2, "type");
			return this;
		}

		public Criteria andModuleIsNull() {
			addCriterion("module is null");
			return this;
		}

		public Criteria andModuleIsNotNull() {
			addCriterion("module is not null");
			return this;
		}

		public Criteria andModuleEqualTo(String value) {
			addCriterion("module =", value, "module");
			return this;
		}

		public Criteria andModuleNotEqualTo(String value) {
			addCriterion("module <>", value, "module");
			return this;
		}

		public Criteria andModuleGreaterThan(String value) {
			addCriterion("module >", value, "module");
			return this;
		}

		public Criteria andModuleGreaterThanOrEqualTo(String value) {
			addCriterion("module >=", value, "module");
			return this;
		}

		public Criteria andModuleLessThan(String value) {
			addCriterion("module <", value, "module");
			return this;
		}

		public Criteria andModuleLessThanOrEqualTo(String value) {
			addCriterion("module <=", value, "module");
			return this;
		}

		public Criteria andModuleLike(String value) {
			addCriterion("module like", value, "module");
			return this;
		}

		public Criteria andModuleNotLike(String value) {
			addCriterion("module not like", value, "module");
			return this;
		}

		public Criteria andModuleIn(List<String> values) {
			addCriterion("module in", values, "module");
			return this;
		}

		public Criteria andModuleNotIn(List<String> values) {
			addCriterion("module not in", values, "module");
			return this;
		}

		public Criteria andModuleBetween(String value1, String value2) {
			addCriterion("module between", value1, value2, "module");
			return this;
		}

		public Criteria andModuleNotBetween(String value1, String value2) {
			addCriterion("module not between", value1, value2, "module");
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
	}
}