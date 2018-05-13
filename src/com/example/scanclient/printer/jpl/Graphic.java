package com.example.scanclient.printer.jpl;

import com.example.scanclient.printer.jpl.JPL.COLOR;

import android.graphics.Point;

public class Graphic extends BaseJPL{

	public Graphic(JPL_Param param) {
		super(param);
	}
	/*
	 * ��ҳ���ڻ����߶� 
	 */
    public boolean line(Point start, Point end, int width, COLOR color)
    {
    	byte[] cmd = { 0x1A, 0x5C, 0x01};
    	port.write(cmd);
    	port.write((short)start.x);
    	port.write((short)start.y);
    	port.write((short)end.x);
    	port.write((short)end.y);
    	port.write((short)width);
    	return port.write((byte)color.ordinal());
    }
    
    public boolean line(Point start, Point end, int width)
    {
        return line(start,end,width,COLOR.Black);
    }
    
    public boolean line(Point start, Point end)
	{
    	byte[] cmd = { 0x1A, 0x5C, 0x00};
    	port.write(cmd);
    	port.write((short)start.x);
    	port.write((short)start.y);
    	port.write((short)end.x);
    	return port.write((short)end.y);
	}

	public boolean rect(int left, int top, int right, int bottom)
	{
		byte[] cmd = {0x1A, 0x26, 0x00};
		port.write(cmd);
		port.write((short)left);
		port.write((short)top);
		port.write((short)right);
		return port.write((short)bottom);
	}
	
	public boolean rect(int left, int top, int right, int bottom, int width, COLOR color)
	{
		byte[] cmd = {0x1A, 0x26, 0x01};
		port.write(cmd);
		port.write((short)left);
		port.write((short)top);
		port.write((short)right);
		port.write((short)bottom);
		port.write((short)width);
		return port.write((byte)color.ordinal());
	}
	
	public boolean rectFill(int left, int top, int right, int bottom, COLOR color)
	{
		byte[] cmd = new byte[]{0x1A, 0x2A, 0x00};
		port.write(cmd);
		port.write((short)left);
		port.write((short)top);
		port.write((short)right);
		port.write((short)bottom);
		return port.write((byte)color.ordinal());
	}
}
