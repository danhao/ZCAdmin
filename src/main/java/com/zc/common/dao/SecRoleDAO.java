package com.zc.common.dao;

import java.util.List;

import com.zc.common.model.SecRole;
import com.zc.common.model.SecRoleExample;

public interface SecRoleDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    int countByExample(SecRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    int deleteByExample(SecRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    Integer insert(SecRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    Integer insertSelective(SecRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    List<SecRole> selectByExample(SecRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    SecRole selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    int updateByExampleSelective(SecRole record, SecRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    int updateByExample(SecRole record, SecRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    int updateByPrimaryKeySelective(SecRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table sec_role
     *
     * @ibatorgenerated Sat Apr 03 23:03:23 CST 2010
     */
    int updateByPrimaryKey(SecRole record);
}