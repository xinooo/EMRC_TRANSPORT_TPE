package com.emrc_transport_tpe;

public class hospital {
	String unit, location, area;
	int level;

	public hospital(String unit, int level, String location, String area) {
		this.unit = unit; //名稱
		this.level = level;  //等級
		this.location = location; //GPS
		this.area = area; //編號
	}
}
