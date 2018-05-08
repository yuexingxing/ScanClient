package com.example.scanclient.db.dao;

import com.example.scanclient.db.DBHelper;
import com.example.scanclient.info.SysUser;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class SysUserDao {

	private SQLiteDatabase db = null;

	public SysUserDao(){

	}

	/**
	 * 单条插入
	 * @param table
	 * @param info
	 * @return
	 */
	public boolean addData(SysUser info) {

		boolean flag = false;
		try {
			db = DBHelper.SQLiteDBHelper.getWritableDatabase();
			db.beginTransaction();

			ContentValues cv = new ContentValues();

			cv.put(DBHelper.UserID, info.getUserID());
			cv.put(DBHelper.EngName, info.getEngName());
			cv.put(DBHelper.ChnName, info.getChnName());
			cv.put(DBHelper.Password, info.getPassword());
			cv.put(DBHelper.OrgID, info.getOrgID());
			cv.put(DBHelper.OrgName, info.getOrgName());
			cv.put(DBHelper.LoginTime, info.getLoginTime());
			
			db.insert(DBHelper.TABLE_SysUser, null, cv);

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
	public void deleteById(SysUser info){

		String sql = " delete from " + DBHelper.TABLE_SysUser
				+ " where " 
				+ DBHelper.UserID + " = '" + info.getUserID() + "'";

		try{
			db = DBHelper.SQLiteDBHelper.getWritableDatabase();
			db.execSQL(sql);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	/**
	 * 清空表
	 */
	public void clearTable(){

		db = DBHelper.SQLiteDBHelper.getWritableDatabase();

		String sql = "delete from " + DBHelper.TABLE_SysUser;
		db.execSQL(sql);
	}
}
