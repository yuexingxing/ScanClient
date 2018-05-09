package com.example.scanclient.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.db.DBHelper;
import com.example.scanclient.info.OrderInfo;
import com.example.scanclient.info.PupDetail;
import com.example.scanclient.info.SysUser;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PupDetailDao {

	private SQLiteDatabase db = null;

	public PupDetailDao(){

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
			cv.put(DBHelper.CargoID, info.getCargoID());
			cv.put(DBHelper.CargoName, info.getCargoName());
			cv.put(DBHelper.Model, info.getModel());
			cv.put(DBHelper.BatchNo, info.getBatchNo());
			cv.put(DBHelper.Count, info.getCount());
			cv.put(DBHelper.Weight, info.getWeight());
			cv.put(DBHelper.Remark, info.getRemark());

			db.insert(DBHelper.TABLE_PupDetail, null, cv);

			db.setTransactionSuccessful();
			
			Log.v("dbdao", "TABLE_PupDetail---addData成功");
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

		String sql = " delete from " + DBHelper.TABLE_PupDetail
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
		String sql = "select * from " + DBHelper.TABLE_PupDetail;

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {

			OrderInfo info = new OrderInfo();
			info.setID(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
			info.setOrderID(cursor.getString(cursor.getColumnIndex(DBHelper.OrderID)));
			info.setCargoID(cursor.getString(cursor.getColumnIndex(DBHelper.CargoID)));
			info.setCargoName(cursor.getString(cursor.getColumnIndex(DBHelper.CargoName)));
			info.setModel(cursor.getString(cursor.getColumnIndex(DBHelper.Model)));
			info.setBatchNo(cursor.getString(cursor.getColumnIndex(DBHelper.BatchNo)));
			info.setCount(cursor.getString(cursor.getColumnIndex(DBHelper.Count)));
			info.setWeight(cursor.getString(cursor.getColumnIndex(DBHelper.Weight)));
			info.setRemark(cursor.getString(cursor.getColumnIndex(DBHelper.Remark)));

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
		String sql = "select * from " + DBHelper.TABLE_PupDetail
				+ " where " 
				+ DBHelper.OrderID + " = '" + orderId + "'";;

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {

			OrderInfo info = new OrderInfo();
			info.setID(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
			info.setOrderID(cursor.getString(cursor.getColumnIndex(DBHelper.OrderID)));
			info.setCargoID(cursor.getString(cursor.getColumnIndex(DBHelper.CargoID)));
			info.setCargoName(cursor.getString(cursor.getColumnIndex(DBHelper.CargoName)));
			info.setModel(cursor.getString(cursor.getColumnIndex(DBHelper.Model)));
			info.setBatchNo(cursor.getString(cursor.getColumnIndex(DBHelper.BatchNo)));
			info.setCount(cursor.getString(cursor.getColumnIndex(DBHelper.Count)));
			info.setWeight(cursor.getString(cursor.getColumnIndex(DBHelper.Weight)));
			info.setRemark(cursor.getString(cursor.getColumnIndex(DBHelper.Remark)));

			list.add(info);
		}

		if(cursor != null){
			cursor.close();
		}

		return list;
	}



	/**
	 * 清空表
	 */
	public void clearTable(){

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();

		String sql = "delete from " + DBHelper.TABLE_PupDetail;
		db.execSQL(sql);
	}
}
