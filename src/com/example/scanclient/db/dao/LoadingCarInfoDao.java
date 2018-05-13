package com.example.scanclient.db.dao;

import java.util.ArrayList;
import java.util.List;
import com.example.scanclient.db.DBHelper;
import com.example.scanclient.info.OrderInfo;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LoadingCarInfoDao {

	private SQLiteDatabase db = null;

	public LoadingCarInfoDao(){

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

			cv.put(DBHelper.Cph, info.getCph());
			cv.put(DBHelper.Driver, info.getDriver());
			cv.put(DBHelper.Telephone, info.getTelephone());
			cv.put(DBHelper.Cellphone, info.getCellphone());
			cv.put(DBHelper.GpsCode, info.getGpsCode());
			cv.put(DBHelper.StationName, info.getStationName());
			cv.put(DBHelper.TransferPhone, info.getTransferPhone());
			cv.put(DBHelper.ScanTime, info.getScanTime());
			cv.put(DBHelper.CrtBillNo, info.getCrtBillNo());
			cv.put(DBHelper.Flag, info.getFlag());
			cv.put(DBHelper.ScanType, info.getScanType());

			db.insert(DBHelper.TABLE_LoadingCarInfo, null, cv);

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
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public int checkData(OrderInfo mOrderInfo) {

		int count = -1;
		Cursor cursor = null;
		String sql = "select * from " + DBHelper.TABLE_LoadingCarInfo
				+ " where " + DBHelper.Cph + "  = '" + mOrderInfo.getCph() + "'" 
				+ " and " + DBHelper.ScanType + " = '"+mOrderInfo.getScanType()+"'";
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
	 * @param n
	 */
	public boolean updateData(OrderInfo info){

		boolean flag = false;
		String sql = "update " + DBHelper.TABLE_LoadingCarInfo 
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

		String sql = " delete from " + DBHelper.TABLE_LoadingCarInfo
				+ " where " 
				+ DBHelper.ScanID + " = '" + info.getScanID() + "'"
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
	public List<OrderInfo> selectAllData() {

		List<OrderInfo> list = new ArrayList<OrderInfo>();

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();
		String sql = "select * from " + DBHelper.TABLE_LoadingCarInfo;

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {

			OrderInfo info = new OrderInfo();
			info.setID(cursor.getString(cursor.getColumnIndex(DBHelper.ScanID)));
			info.setOrderID(cursor.getString(cursor.getColumnIndex(DBHelper.Cph)));
			info.setCargoID(cursor.getString(cursor.getColumnIndex(DBHelper.Driver)));
			info.setCargoName(cursor.getString(cursor.getColumnIndex(DBHelper.Telephone)));
			info.setModel(cursor.getString(cursor.getColumnIndex(DBHelper.Cellphone)));
			info.setBatchNo(cursor.getString(cursor.getColumnIndex(DBHelper.GpsCode)));
			info.setCount(cursor.getString(cursor.getColumnIndex(DBHelper.StationName)));
			info.setWeight(cursor.getString(cursor.getColumnIndex(DBHelper.TransferPhone)));
			info.setScanTime(cursor.getString(cursor.getColumnIndex(DBHelper.ScanTime)));
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
		String sql = "select * from " + DBHelper.TABLE_LoadingCarInfo
				+ " where " 
				+ DBHelper.OrderID + " = '" + orderId + "'";;

				Cursor cursor = db.rawQuery(sql, null);

				while(cursor.moveToNext()) {

					OrderInfo info = new OrderInfo();
					info.setScanID(cursor.getString(cursor.getColumnIndex(DBHelper.ScanID)));
					info.setOrderID(cursor.getString(cursor.getColumnIndex(DBHelper.Cph)));
					info.setCargoID(cursor.getString(cursor.getColumnIndex(DBHelper.Driver)));
					info.setCargoName(cursor.getString(cursor.getColumnIndex(DBHelper.Telephone)));
					info.setModel(cursor.getString(cursor.getColumnIndex(DBHelper.Cellphone)));
					info.setBatchNo(cursor.getString(cursor.getColumnIndex(DBHelper.GpsCode)));
					info.setCount(cursor.getString(cursor.getColumnIndex(DBHelper.StationName)));
					info.setWeight(cursor.getString(cursor.getColumnIndex(DBHelper.TransferPhone)));
					info.setScanTime(cursor.getString(cursor.getColumnIndex(DBHelper.ScanTime)));
					info.setCrtBillNo(cursor.getString(cursor.getColumnIndex(DBHelper.CrtBillNo)));
					info.setFlag(cursor.getString(cursor.getColumnIndex(DBHelper.Flag)));

					list.add(info);
				}

				if(cursor != null){
					cursor.close();
				}

				return list;
	}


	public int getMax(){

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();

		String sql = "select max('"+DBHelper.ScanID+"') as aa from " + DBHelper.TABLE_LoadingCarInfo;
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

		String sql = "delete from " + DBHelper.TABLE_LoadingCarInfo;
		db.execSQL(sql);
	}
}
