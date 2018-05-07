package com.example.scanclient.info;

public class BillInfo {

	private String billcode;
	private String status;
	private String scanTime;
	private String cusBillcode;
	private boolean flag;
	
	public String getBillcode() {
		return billcode;
	}
	public void setBillcode(String billcode) {
		this.billcode = billcode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getScanTime() {
		return scanTime;
	}
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	public String getCusBillcode() {
		return cusBillcode;
	}
	public void setCusBillcode(String cusBillcode) {
		this.cusBillcode = cusBillcode;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
