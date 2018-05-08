package com.example.scanclient.info;

public class SysCode {

	private String ID;//		nvarchar(10)    not null primary key,
	private String Name;//	    nvarchar(50)	null,
	private String Type;//	    nvarchar(50)	null,

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}

}
