package com.example.scanclient.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.db.DBHelper;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.info.PupScan;
import com.example.scanclient.info.SysUser;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PupScanDao {

	private SQLiteDatabase db = null;

	public PupScanDao(){

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

			cv.put(DBHelper.OrderID, info.getOrderID());
			cv.put(DBHelper.BatchNo, info.getBatchNo());
			cv.put(DBHelper.CargoID, info.getCargoID());
			cv.put(DBHelper.Count, info.getCount());
			cv.put(DBHelper.Weight, info.getWeight());
			cv.put(DBHelper.Remark, info.getRemark());
			cv.put(DBHelper.ScanTime, info.getScanTime());
			cv.put(DBHelper.ScanUserID, info.getScanUserID());
			cv.put(DBHelper.CrtBillNo, info.getCrtBillNo());
			cv.put(DBHelper.Flag, info.getFlag());

			db.insert(DBHelper.TABLE_PupScan, null, cv);

			db.setTransactionSuccessful();
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
	public void deleteById(OrderInfo info){

		String sql = " delete from " + DBHelper.TABLE_PupScan
				+ " where " + DBHelper.OrderID + " = '" + info.getOrderID() + "'"
				+ " and " + DBHelper.CargoID + " = '"+info.getCargoID()+"'";

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
		String sql = "select * from " + DBHelper.TABLE_PupScan;

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {

			OrderInfo info = new OrderInfo();
			info.setID(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
			info.setOrderID(cursor.getString(cursor.getColumnIndex(DBHelper.OrderID)));
			info.setBatchNo(cursor.getString(cursor.getColumnIndex(DBHelper.BatchNo)));
			info.setCargoID(cursor.getString(cursor.getColumnIndex(DBHelper.CargoID)));
			info.setCount(cursor.getString(cursor.getColumnIndex(DBHelper.Count)));
			info.setWeight(cursor.getString(cursor.getColumnIndex(DBHelper.Weight)));
			info.setRemark(cursor.getString(cursor.getColumnIndex(DBHelper.Remark)));
			info.setScanTime(cursor.getString(cursor.getColumnIndex(DBHelper.ScanTime)));
			info.setScanUserID(cursor.getString(cursor.getColumnIndex(DBHelper.ScanUserID)));
			info.setCrtBillNo(cursor.getString(cursor.getColumnIndex(DBHelper.CrtBillNo)));
			info.setFlag(cursor.getString(cursor.getColumnIndex(DBHelper.Flag)));

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
	public List<OrderInfo> selectDataById(String orderId) {

		List<OrderInfo> list = new ArrayList<OrderInfo>();

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();
		String sql = "select * from " + DBHelper.TABLE_PupScan
				+ " where " 
				+ DBHelper.OrderID + " = '" + orderId + "'"
				+ " order by " + DBHelper.ScanTime + " desc";

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {

			OrderInfo info = new OrderInfo();
			info.setID(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
			info.setOrderID(cursor.getString(cursor.getColumnIndex(DBHelper.OrderID)));
			info.setBatchNo(cursor.getString(cursor.getColumnIndex(DBHelper.BatchNo)));
			info.setCargoID(cursor.getString(cursor.getColumnIndex(DBHelper.CargoID)));
			info.setCount(cursor.getString(cursor.getColumnIndex(DBHelper.Count)));
			info.setWeight(cursor.getString(cursor.getColumnIndex(DBHelper.Weight)));
			info.setRemark(cursor.getString(cursor.getColumnIndex(DBHelper.Remark)));
			info.setScanTime(cursor.getString(cursor.getColumnIndex(DBHelper.ScanTime)));
			info.setScanUserID(cursor.getString(cursor.getColumnIndex(DBHelper.ScanUserID)));
			info.setCrtBillNo(cursor.getString(cursor.getColumnIndex(DBHelper.CrtBillNo)));
			info.setFlag(cursor.getString(cursor.getColumnIndex(DBHelper.Flag)));

			list.add(info);
		}

		if(cursor != null){
			cursor.close();
		}

		return list;
	}

	/**
	 * @param
	 * @param n
	 */
	public boolean updateData(OrderInfo info){

		boolean flag = false;
		String sql = "update " + DBHelper.TABLE_PupScan 
				+ " set " 
				+ DBHelper.Flag + " = '" + info.getFlag() + "'"
				+ " where " + DBHelper.CargoID + " = '" + info.getCargoID() + "'"
				+ " and " + DBHelper.OrderID + " = '" + info.getOrderID() + "'";
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
	 * 清空表
	 */
	public void clearTable(){

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();

		String sql = "delete from " + DBHelper.TABLE_PupScan;
		db.execSQL(sql);
	}
}
