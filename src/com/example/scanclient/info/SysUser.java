package com.example.scanclient.info;

public class SysUser {

	 private String UserID;//		nvarchar(10)    not null primary key,
	 private String EngName;//	    nvarchar(50)	null,
	 private String ChnName;//	    nvarchar(50)	null,
	 private String Password;//    nvarchar(50)    null,
	 private String OrgID;//       nvarchar(10)    null,
	 private String OrgName;//	    nvarchar(50)	null,
	 private String LoginTime;//   datetime        null default(getdate())
	 
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getEngName() {
		return EngName;
	}
	public void setEngName(String engName) {
		EngName = engName;
	}
	public String getChnName() {
		return ChnName;
	}
	public void setChnName(String chnName) {
		ChnName = chnName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getOrgID() {
		return OrgID;
	}
	public void setOrgID(String orgID) {
		OrgID = orgID;
	}
	public String getOrgName() {
		return OrgName;
	}
	public void setOrgName(String orgName) {
		OrgName = orgName;
	}
	public String getLoginTime() {
		return LoginTime;
	}
	public void setLoginTime(String loginTime) {
		LoginTime = loginTime;
	}
}
