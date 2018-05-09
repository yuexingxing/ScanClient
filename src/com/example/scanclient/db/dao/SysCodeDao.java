package com.example.scanclient.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.scanclient.db.DBHelper;
import com.example.scanclient.info.PupScan;
import com.example.scanclient.info.SysCode;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SysCodeDao {

	private SQLiteDatabase db = null;

	public SysCodeDao(){

	}

	/**
	 * 单条插入
	 * @param table
	 * @param info
	 * @return
	 */
	public boolean addData(SysCode info) {

		boolean flag = false;
		try {
			db = DBHelper.SQLiteDBHelper.getWritableDatabase();
			db.beginTransaction();

			ContentValues cv = new ContentValues();

			cv.put(DBHelper.ID, info.getID());
			cv.put(DBHelper.Name, info.getName());
			cv.put(DBHelper.Type, info.getType());

			db.insert(DBHelper.TABLE_SysCode, null, cv);

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
	public void deleteById(SysCode info){

		String sql = " delete from " + DBHelper.TABLE_SysCode
				+ " where " 
				+ DBHelper.ID + " = '" + info.getID() + "'";

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
	public List<SysCode> selectAllData() {

		List<SysCode> list = new ArrayList<SysCode>();

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();
		String sql = "select * from " + DBHelper.TABLE_SysCode;

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {

			SysCode info = new SysCode();
			info.setID(cursor.getString(cursor.getColumnIndex(DBHelper.ID)));
			info.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Name)));
			info.setType(cursor.getString(cursor.getColumnIndex(DBHelper.Type)));

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
		String sql = "select * from " + DBHelper.TABLE_SysCode + " where " + DBHelper.ID + "  = '" + id + "'";
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

		String sql = "delete from " + DBHelper.TABLE_SysCode;
		db.execSQL(sql);
	}
}
