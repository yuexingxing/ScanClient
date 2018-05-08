package com.example.scanclient.info;

public class PupDetail {

	 private String ID;//          bigint          not null identity(1,1) primary key,
	 private String OrderID;//		nvarchar(20)    not null,
	 private String CargoID;//     nvarchar(30)    null,
	 private String CargoName;//   nvarchar(30)	null,
	 private String Model;//       nvarchar(20)    null,
	 private String BatchNo;//     nvarchar(20)    null,
	 private String Count;//       int             null,
	 private String Weight;//      decimal(10,2)   null,
	 private String Remark;//      nvarchar(50)    null
	 
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
	public String getCargoID() {
		return CargoID;
	}
	public void setCargoID(String cargoID) {
		CargoID = cargoID;
	}
	public String getCargoName() {
		return CargoName;
	}
	public void setCargoName(String cargoName) {
		CargoName = cargoName;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getBatchNo() {
		return BatchNo;
	}
	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
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
}
