package com.zlebank.zplatform.member.pojo;

// Generated 2016-8-18 17:46:50 by Hibernate Tools 4.0.0

import java.util.Date;

import org.junit.runners.Parameterized.Parameter;

/**
 * TMemberApply generated by hbm2java
 */
public class PojoMemberApply implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -935102899779534028L;
	private long selfId;
	private long memId;
	private String memberId;
	private String instiCode;
	private String memberName;
	private String loginName;
	private String pwd;
	private String payPwd;
	private String realnameLv;
	private String phone;
	private String email;
	private String memberType;
	private String status;
	private String registerIdent;
	private Date lockTime;
	private Date inTime;
	private Date upTime;

	public PojoMemberApply() {
	}

	public PojoMemberApply(long selfId, long memId, String memberId, String pwd,
			String memberType, String status, String registerIdent) {
		this.selfId = selfId;
		this.memId = memId;
		this.memberId = memberId;
		this.pwd = pwd;
		this.memberType = memberType;
		this.status = status;
		this.registerIdent = registerIdent;
	}

	public PojoMemberApply(long selfId, long memId, String memberId,
			String instiCode, String memberName, String loginName, String pwd,
			String payPwd, String realnameLv, String phone, String email,
			String memberType, String status, String registerIdent,
			Date lockTime, Date inTime, Date upTime) {
		this.selfId = selfId;
		this.memId = memId;
		this.memberId = memberId;
		this.instiCode = instiCode;
		this.memberName = memberName;
		this.loginName = loginName;
		this.pwd = pwd;
		this.payPwd = payPwd;
		this.realnameLv = realnameLv;
		this.phone = phone;
		this.email = email;
		this.memberType = memberType;
		this.status = status;
		this.registerIdent = registerIdent;
		this.lockTime = lockTime;
		this.inTime = inTime;
		this.upTime = upTime;
	}

	/**
	 * @return the selfId
	 */
	public long getSelfId() {
		return selfId;
	}

	/**
	 * @param selfId the selfId to set
	 */
	public void setSelfId(long selfId) {
		this.selfId = selfId;
	}

	/**
	 * @return the memId
	 */
	public long getMemId() {
		return memId;
	}

	/**
	 * @param memId the memId to set
	 */
	public void setMemId(long memId) {
		this.memId = memId;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the instiCode
	 */
	public String getInstiCode() {
		return instiCode;
	}

	/**
	 * @param instiCode the instiCode to set
	 */
	public void setInstiCode(String instiCode) {
		this.instiCode = instiCode;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the payPwd
	 */
	public String getPayPwd() {
		return payPwd;
	}

	/**
	 * @param payPwd the payPwd to set
	 */
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	/**
	 * @return the realnameLv
	 */
	public String getRealnameLv() {
		return realnameLv;
	}

	/**
	 * @param realnameLv the realnameLv to set
	 */
	public void setRealnameLv(String realnameLv) {
		this.realnameLv = realnameLv;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the registerIdent
	 */
	public String getRegisterIdent() {
		return registerIdent;
	}

	/**
	 * @param registerIdent the registerIdent to set
	 */
	public void setRegisterIdent(String registerIdent) {
		this.registerIdent = registerIdent;
	}

	/**
	 * @return the lockTime
	 */
	public Date getLockTime() {
		return lockTime;
	}

	/**
	 * @param lockTime the lockTime to set
	 */
	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	/**
	 * @return the inTime
	 */
	public Date getInTime() {
		return inTime;
	}

	/**
	 * @param inTime the inTime to set
	 */
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	/**
	 * @return the upTime
	 */
	public Date getUpTime() {
		return upTime;
	}

	/**
	 * @param upTime the upTime to set
	 */
	public void setUpTime(Date upTime) {
		this.upTime = upTime;
	}
	
	
}