package com.zc.common.model;

public class SecUserRoleKey extends BaseModel {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sec_user_role.role_id
     *
     * @ibatorgenerated Tue Apr 06 15:33:49 CST 2010
     */
    private Integer roleId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column sec_user_role.user_id
     *
     * @ibatorgenerated Tue Apr 06 15:33:49 CST 2010
     */
    private Integer userId;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sec_user_role.role_id
     *
     * @return the value of sec_user_role.role_id
     *
     * @ibatorgenerated Tue Apr 06 15:33:49 CST 2010
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sec_user_role.role_id
     *
     * @param roleId the value for sec_user_role.role_id
     *
     * @ibatorgenerated Tue Apr 06 15:33:49 CST 2010
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column sec_user_role.user_id
     *
     * @return the value of sec_user_role.user_id
     *
     * @ibatorgenerated Tue Apr 06 15:33:49 CST 2010
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column sec_user_role.user_id
     *
     * @param userId the value for sec_user_role.user_id
     *
     * @ibatorgenerated Tue Apr 06 15:33:49 CST 2010
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}