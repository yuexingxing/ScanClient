package com.example.scanclient.info;

public class PupHeader {

	private String ID;//          bigint          not null identity(1,1) primary key,
	private String OrderID;//		nvarchar(20)    not null,
	private String OrderDate;//   datetime	    null,
	private String Status;//	    nvarchar(10)	null,
	private String CrtBillNo;//   nvarchar(20)    null,   -- 系统自动生成的标识号
	private String IsPrinted;//   int	            not null default(0), -- 0 : not print; > 1 print count
	private String Flag;//        int             null default(0)     -- 0:not saved; 1:confirm saved; 
	
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
	public String getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getCrtBillNo() {
		return CrtBillNo;
	}
	public void setCrtBillNo(String crtBillNo) {
		CrtBillNo = crtBillNo;
	}
	public String getIsPrinted() {
		return IsPrinted;
	}
	public void setIsPrinted(String isPrinted) {
		IsPrinted = isPrinted;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
}
