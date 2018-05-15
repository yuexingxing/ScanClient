package com.example.scanclient.printer.jpl;

import com.example.scanclient.printer.Port;


public class JPL_Param {
	public Port port;
	public int pageWidth;
	public int pageHeight;
	public JPL_Param(Port port)
	{
		this.port = port;
		pageWidth = -1;
		pageHeight = -1;
	}
}
