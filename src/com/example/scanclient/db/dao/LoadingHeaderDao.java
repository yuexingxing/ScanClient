package com.example.scanclient.db.dao;

import java.util.ArrayList;
import java.util.List;
import com.example.scanclient.db.DBHelper;
import com.example.scanclient.info.OrderInfo;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LoadingHeaderDao {

	private SQLiteDatabase db = null;

	public LoadingHeaderDao(){

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

			cv.put(DBHelper.ScanID, info.getScanID());
			cv.put(DBHelper.OrderID, info.getOrderID());
			cv.put(DBHelper.OrderDate, info.getOrderDate());
			cv.put(DBHelper.Status, info.getStatus());
			cv.put(DBHelper.ScanType, info.getScanType());

			db.insert(DBHelper.TABLE_LoadingHeader, null, cv);

			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			db.endTransaction();
		}
		return flag;
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public int checkData(OrderInfo info) {

		int count = -1;
		Cursor cursor = null;
		String sql = "select * from " + DBHelper.TABLE_LoadingHeader
				+ " where " + DBHelper.OrderID + "  = '" + info.getOrderID()
				+ " and " + DBHelper.ScanType + " = '"+info.getScanType()+"'";
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
	 * 设置快递公司的常用与不常用
	 * @param info(0、1)不常用、常用
	 * @param n
	 */
	public boolean updateData(OrderInfo info){

		boolean flag = false;
		String sql = "update " + DBHelper.TABLE_LoadingHeader 
				+ " set " 
				+ DBHelper.Driver + " = '" + info.getDriver() + "'" + ","
				+ DBHelper.Telephone + " = '" + info.getTelephone() + "'" + ","
				+ DBHelper.Cellphone + " = '" + info.getCellphone() + "'" + ","
				+ DBHelper.GpsCode + " = '" + info.getGpsCode() + "'" + ","
				+ DBHelper.StationName + " = '" + info.getStationName() + "'" + ","
				+ DBHelper.TransferPhone + " = '" + info.getTransferPhone() + "'" + ","
				+ DBHelper.ScanTime + " = '" + info.getScanTime() + "'"
				+ " where " + DBHelper.Cph + " = '" + info.getCph() + "'"
				+ " and " + DBHelper.ScanType + " = '"+info.getScanType()+"'";
		try{
			db = DBHelper.SQLiteDBHelper.getWritableDatabase();
			db.beginTransaction();
			db.execSQL(sql);
			db.setTransactionSuccessful();
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}

		return flag;
	}

	/**
	 * 删除数据
	 * @param info
	 */
	public void deleteById(OrderInfo info){

		String sql = " delete from " + DBHelper.TABLE_LoadingHeader
				+ " where " 
				+ DBHelper.OrderID + " = '" + info.getOrderID() + "'"
				+ " and " + DBHelper.ScanID + " = '" + info.getScanID() + "'"
				+ " and " + DBHelper.ScanType + " = '"+info.getScanType()+"'";
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
	public List<OrderInfo> selectAllData(OrderInfo info) {

		List<OrderInfo> list = new ArrayList<OrderInfo>();

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();
		String sql = "select * from " + DBHelper.TABLE_LoadingHeader
				+ " where " + DBHelper.ScanType + " = '"+info.getScanType()+"'";

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {

			info = new OrderInfo();
			info.setID(cursor.getString(cursor.getColumnIndex(DBHelper.ScanID)));
			info.setOrderID(cursor.getString(cursor.getColumnIndex(DBHelper.Cph)));
			info.setOrderDate(cursor.getString(cursor.getColumnIndex(DBHelper.OrderDate)));
			info.setStatus(cursor.getString(cursor.getColumnIndex(DBHelper.Status)));
			info.setScanType(cursor.getString(cursor.getColumnIndex(DBHelper.ScanType)));

			list.add(info);
		}

		if(cursor != null){
			cursor.close();
		}

		return list;
	}

	/**
	 * @param id
	 * @return
	 */
	public List<OrderInfo> selectDataById(OrderInfo info) {

		List<OrderInfo> list = new ArrayList<OrderInfo>();

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();
		String sql = "select * from " + DBHelper.TABLE_LoadingHeader
				+ " where " + DBHelper.ScanID + " = '" + info.getScanID() + "'"
				+ " and " + DBHelper.ScanType + " = '"+info.getScanType()+"'";

				Cursor cursor = db.rawQuery(sql, null);

				while(cursor.moveToNext()) {

					info = new OrderInfo();
					info.setID(cursor.getString(cursor.getColumnIndex(DBHelper.ScanID)));
					info.setOrderID(cursor.getString(cursor.getColumnIndex(DBHelper.OrderID)));
					info.setOrderDate(cursor.getString(cursor.getColumnIndex(DBHelper.OrderDate)));
					info.setStatus(cursor.getString(cursor.getColumnIndex(DBHelper.Status)));
					info.setScanType(cursor.getString(cursor.getColumnIndex(DBHelper.ScanType)));

					list.add(info);
				}

				if(cursor != null){
					cursor.close();
				}

				return list;
	}


	public int getMax(OrderInfo info){

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();

		String sql = "select max('"+DBHelper.ScanID+"') as aa from " + DBHelper.TABLE_LoadingHeader
				+ " where " + DBHelper.ScanType + " = '"+info.getScanType()+"'";
		Cursor cursor = db.rawQuery(sql, null);

		if(cursor.moveToNext()){

			int n = cursor.getInt(0);
			Log.v("zd", n + "");
		}

		if(cursor != null){
			cursor.close();
		}

		return -1;
	}

	/**
	 * 清空表
	 */
	public void clearTable(){

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();

		String sql = "delete from " + DBHelper.TABLE_LoadingHeader;
		db.execSQL(sql);
	}
}
