package com.example.scanclient.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.db.DBHelper;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.info.PupDetail;
import com.example.scanclient.info.PupHeader;
import com.example.scanclient.info.SysUser;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PupHeaderDao {

	private SQLiteDatabase db = null;

	public PupHeaderDao(){

	}

	/**
	 * 单条插入
	 * @param table
	 * @param info
	 * @return
	 */
	public boolean addData(OrderInfo info) {

		boolean flag = false;
		try {
			db = DBHelper.SQLiteDBHelper.getWritableDatabase();
			db.beginTransaction();

			ContentValues cv = new ContentValues();

			cv.put(DBHelper.ID, info.getID());
			cv.put(DBHelper.OrderID, info.getOrderID());
			cv.put(DBHelper.OrderDate, info.getOrderDate());
			cv.put(DBHelper.Status, info.getStatus());
			cv.put(DBHelper.CrtBillNo, info.getCrtBillNo());
			cv.put(DBHelper.IsPrinted, info.getIsPrinted());
			cv.put(DBHelper.Flag, info.getFlag());

			db.insert(DBHelper.TABLE_PupHeader, null, cv);

			db.setTransactionSuccessful();
			Log.v("dbdao", "TABLE_PupHeader---addData成功");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			db.endTransaction();
		}
		return flag;
	}

	/**
	 * 删除数据
	 * @param info
	 */
	public void deleteById(String id){

		String sql = " delete from " + DBHelper.TABLE_PupHeader
				+ " where " 
				+ DBHelper.OrderID + " = '" + id + "'";

		try{
			db = DBHelper.SQLiteDBHelper.getWritableDatabase();
			db.execSQL(sql);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	/**
	 * @param id
	 * @return
	 */
	public List<OrderInfo> selectAllData() {

		List<OrderInfo> list = new ArrayList<OrderInfo>();

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();
		String sql = "select * from " + DBHelper.TABLE_PupHeader;

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {

			OrderInfo info = new OrderInfo();
			info.setID(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
			info.setOrderID(cursor.getString(cursor.getColumnIndex(DBHelper.OrderID)));
			info.setOrderDate(cursor.getString(cursor.getColumnIndex(DBHelper.OrderDate)));
			info.setStatus(cursor.getString(cursor.getColumnIndex(DBHelper.Status)));
			info.setCrtBillNo(cursor.getString(cursor.getColumnIndex(DBHelper.CrtBillNo)));
			info.setIsPrinted(cursor.getString(cursor.getColumnIndex(DBHelper.IsPrinted)));
			info.setFlag(cursor.getString(cursor.getColumnIndex(DBHelper.Flag)));

			list.add(info);
		}

		if(cursor != null){
			cursor.close();
		}

		return list;
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public int checkData(String id) {

		int count = -1;
		Cursor cursor = null;
		String sql = "select * from " + DBHelper.TABLE_PupHeader + " where " + DBHelper.OrderID + "  = '" + id + "'";
		try{

			db = DBHelper.SQLiteDBHelper.getWritableDatabase();
			cursor = db.rawQuery(sql, null);
			count = cursor.getCount();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(cursor != null){
				cursor.close();
			}
		}

		return count;
	}

	/**
	 * 清空表
	 */
	public void clearTable(){

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();

		String sql = "delete from " + DBHelper.TABLE_PupHeader;
		db.execSQL(sql);
	}
}
