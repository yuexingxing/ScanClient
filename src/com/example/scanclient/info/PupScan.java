package com.example.scanclient.info;

public class PupScan {

	 private String ID;//          bigint          not null identity(1,1) primary key,
	 private String OrderID;//		nvarchar(20)    not null,
	 private String BatchNo;//     nvarchar(6)     null,
	 private String CargoID;//     nvarchar(30)    null,
	 private String Count;//       int             null default(0),
	 private String Weight;//      decimal(10,2)   null default(0),
	 private String Remark;//      nvarchar(50)    null,
	 private String ScanTime;//    datetime        null default(getdate()),
	 private String ScanUserID;//  nvarchar(10)    null,
	 private String CrtBillNo;//   nvarchar(20)    null,   -- 系统自动生成的标识号
	 private String Flag;//        int             null de
	 
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	public String getBatchNo() {
		return BatchNo;
	}
	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}
	public String getCargoID() {
		return CargoID;
	}
	public void setCargoID(String cargoID) {
		CargoID = cargoID;
	}
	public String getCount() {
		return Count;
	}
	public void setCount(String count) {
		Count = count;
	}
	public String getWeight() {
		return Weight;
	}
	public void setWeight(String weight) {
		Weight = weight;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getScanTime() {
		return ScanTime;
	}
	public void setScanTime(String scanTime) {
		ScanTime = scanTime;
	}
	public String getScanUserID() {
		return ScanUserID;
	}
	public void setScanUserID(String scanUserID) {
		ScanUserID = scanUserID;
	}
	public String getCrtBillNo() {
		return CrtBillNo;
	}
	public void setCrtBillNo(String crtBillNo) {
		CrtBillNo = crtBillNo;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
	 
	
}
