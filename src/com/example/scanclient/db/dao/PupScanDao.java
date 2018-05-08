package com.example.scanclient.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.db.DBHelper;
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
	public boolean addData(PupScan info) {

		boolean flag = false;
		try {
			db = DBHelper.SQLiteDBHelper.getWritableDatabase();
			db.beginTransaction();

			ContentValues cv = new ContentValues();

			cv.put(DBHelper.ID, info.getID());
			cv.put(DBHelper.OrderID, info.getOrderID());
			cv.put(DBHelper.BatchNo, info.getBatchNo());
			cv.put(DBHelper.CargoID, info.getCargoID());
			cv.put(DBHelper.Count, info.getCount());
			cv.put(DBHelper.Weight, info.getWeight());
			cv.put(DBHelper.Remark, info.getRemark());
			cv.put(DBHelper.Weight, info.getWeight());
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
	public void deleteById(PupScan info){

		String sql = " delete from " + DBHelper.TABLE_PupScan
				+ " where " 
				+ DBHelper.OrderID + " = '" + info.getOrderID() + "'";

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
	public List<PupScan> selectAllData() {

		List<PupScan> list = new ArrayList<PupScan>();

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();
		String sql = "select * from " + DBHelper.TABLE_PupScan;

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {

			PupScan info = new PupScan();
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
	 * 清空表
	 */
	public void clearTable(){

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();

		String sql = "delete from " + DBHelper.TABLE_PupScan;
		db.execSQL(sql);
	}
}
