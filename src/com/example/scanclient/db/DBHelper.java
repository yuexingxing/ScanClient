package com.example.scanclient.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/** 
 * 
 * 
 * @author yxx
 *
 * @date 2016-5-28 下午2:55:05
 * 
 */
public final class DBHelper {

	public static final int DATABASE_VERSION = 1;// 数据库版本号

	public static SQLiteDBOpenHelper SQLiteDBHelper;
	public static final String DATABASE_NAME = "ScanClient.db";// 数据库名

	public static final String TABLE_SysCode = "SysCode";
	public static final String TABLE_SysUser = "SysUser";
	public static final String TABLE_SysUserRights = "SysUserRights";
	public static final String TABLE_PupHeader = "PupHeader";
	public static final String TABLE_PupDetail = "PupDetail";
	public static final String TABLE_PupScan = "PupScan";
	public static final String TABLE_LoadingCarInfo = "LoadingCarInfo";
	public static final String TABLE_LoadingHeader = "LoadingHeader";
	public static final String TABLE_LoadingScan = "LoadingScan";
	public static final String TABLE_LoadingDetail = "LoadingDetail";
	public static final String TABLE_UnloadingCarInfo = "UnloadingCarInfo";
	public static final String TABLE_UnloadingHeader = "UnloadingHeader";
	public static final String TABLE_UnloadingDetail = "UnloadingDetail";
	public static final String TABLE_UnloadingScan = "UnloadingScan";
	public static final String TABLE_PodConsignee = "PodConsignee";
	public static final String TABLE_PodHeader = "PodHeader";
	public static final String TABLE_PodDetail = "PodDetail";
	public static final String TABLE_PodScan = "PodScan";
	public static final String TABLE_ExPhoto = "ExPhoto";

	/**
	 * 数据库版本号
	 * 每次修改表中字段时，该版本号自动+1
	 * 避免PDA上程序卸载重装
	 * 修改后表被删除(包括表中数据)，创建一个新表
	 * */

	/**
	 * 数据扫描表
	 */
	public static final String ID = "ID";
	public static final String Name = "Name";
	public static final String Type = "Type";
	public static final String CREATE_TABLE_SysCode = "create table "
			+ TABLE_SysCode + " (" 
			+ ID + "  nvarchar(50) null default(''), "
			+ Name + "  nvarchar(50) null default(''), "
			+ Type + " nvarchar(50) null default(''));";

	public static final String UserID = "UserID";
	public static final String EngName = "EngName";
	public static final String ChnName = "ChnName";
	public static final String Password = "Password";
	public static final String OrgID = "OrgID";
	public static final String OrgName = "OrgName";
	public static final String LoginTime = "LoginTime";
	public static final String CREATE_TABLE_SysUser = "create table "
			+ TABLE_SysUser + " (" 
			+ UserID + "  nvarchar(50) null default(''), "
			+ EngName + "  nvarchar(50) null default(''), "
			+ ChnName + "  nvarchar(50) null default(''), "
			+ Password + "  nvarchar(50) null default(''), "
			+ OrgID + "  nvarchar(50) null default(''), "
			+ OrgName + "  nvarchar(50) null default(''), "
			+ LoginTime + " nvarchar(50) null default(''));";

	public static final String Rights = "Rights";
	public static final String CREATE_SysUserRights = "create table "
			+ TABLE_SysUserRights + " (" 
			+ UserID + "  nvarchar(50) null default(''), "
			+ Rights + " nvarchar(50) null default(''));";

	public static final String OrderID = "OrderID";
	public static final String OrderDate = "OrderDate";
	public static final String Status = "Status";
	public static final String CrtBillNo = "CrtBillNo";
	public static final String IsPrinted = "IsPrinted";
	public static final String Flag = "Flag";
	public static final String CREATE_PupHeader = "create table "
			+ TABLE_PupHeader + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ OrderDate + "  nvarchar(50) null default(''), "
			+ Status + "  nvarchar(50) null default(''), "
			+ CrtBillNo + "  nvarchar(50) null default(''), "
			+ IsPrinted + "  nvarchar(50) null default(''), "
			+ Flag + " nvarchar(50) null default(''));";

	public static final String CargoID = "CargoID";
	public static final String CargoName = "CargoName";
	public static final String Model = "Model";
	public static final String BatchNo = "BatchNo";
	public static final String Count = "Count";
	public static final String Weight = "Weight";
	public static final String Remark = "Remark";
	public static final String CREATE_PupDetail = "create table "
			+ TABLE_PupDetail + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ CargoID + "  nvarchar(50) null default(''), "
			+ CargoName + "  nvarchar(50) null default(''), "
			+ Model + "  nvarchar(50) null default(''), "
			+ BatchNo + "  nvarchar(50) null default(''), "
			+ Count + "  nvarchar(50) null default(''), "
			+ Weight + "  nvarchar(50) null default(''), "
			+ Remark + " nvarchar(50) null default(''));";

	public static final String ScanTime = "ScanTime";
	public static final String ScanUserID = "ScanUserID";
	public static final String CREATE_PupScan = "create table "
			+ TABLE_PupScan + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ BatchNo + "  nvarchar(50) null default(''), "
			+ CargoID + "  nvarchar(50) null default(''), "
			+ Count + "  nvarchar(50) null default(''), "
			+ Weight + "  nvarchar(50) null default(''), "
			+ Remark + "  nvarchar(50) null default(''), "
			+ ScanTime + "  nvarchar(50) null default(''), "
			+ ScanUserID + "  nvarchar(50) null default(''), "
			+ CrtBillNo + "  nvarchar(50) null default(''), "
			+ Flag + " nvarchar(50) null default(''));";

	public static final String ScanID = "ScanID";
	public static final String Cph = "Cph";
	public static final String Driver = "Driver";
	public static final String Telephone = "Telephone";
	public static final String Cellphone = "Cellphone";
	public static final String GpsCode = "GpsCode";
	public static final String StationName = "StationName";
	public static final String TransferPhone = "TransferPhone";
	public static final String ScanType = "ScanType";//扫描类型，装车，卸车
	public static final String CREATE_LoadingCarInfo = "create table "
			+ TABLE_LoadingCarInfo + " (" 
			+ ScanID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Cph + "  nvarchar(50) null default(''), " 
			+ Driver + "  nvarchar(50) null default(''), "
			+ Telephone + "  nvarchar(50) null default(''), "
			+ Cellphone + "  nvarchar(50) null default(''), "
			+ GpsCode + "  nvarchar(50) null default(''), "
			+ StationName + "  nvarchar(50) null default(''), "
			+ TransferPhone + "  nvarchar(50) null default(''), "
			+ ScanTime + "  nvarchar(50) null default(''), "
			+ CrtBillNo + "  nvarchar(50) null default(''), "
			+ ScanType + "  nvarchar(50) null default(''), "
			+ Flag + " nvarchar(50) null default(''));";


	public static final String CREATE_LoadingHeader = "create table "
			+ TABLE_LoadingHeader + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ScanID + "  nvarchar(50) null default(''), "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ OrderDate + "  nvarchar(50) null default(''), "
			+ ScanType + "  nvarchar(50) null default(''), "
			+ Status + " nvarchar(50) null default(''));";

	public static final String CREATE_LoadingDetail = "create table "
			+ TABLE_LoadingDetail + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ScanID + "  nvarchar(50) null default(''), "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ CargoID + "  nvarchar(50) null default(''), "
			+ CargoName + "  nvarchar(50) null default(''), "
			+ Model + "  nvarchar(50) null default(''), "
			+ BatchNo + "  nvarchar(50) null default(''), "
			+ Count + "  nvarchar(50) null default(''), "
			+ Weight + "  nvarchar(50) null default(''), "
			+ ScanType + "  nvarchar(50) null default(''), "
			+ Remark + " nvarchar(50) null default(''));";

	public static final String CREATE_LoadingScan = "create table "
			+ TABLE_LoadingScan + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ScanID + "  nvarchar(50) null default(''), "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ CargoID + "  nvarchar(50) null default(''), "
			+ Count + "  nvarchar(50) null default(''), "
			+ Remark + "  nvarchar(50) null default(''), "
			+ ScanTime + "  nvarchar(50) null default(''), "
			+ ScanUserID + "  nvarchar(50) null default(''), "
			+ ScanType + "  nvarchar(50) null default(''), "
			+ Flag + " nvarchar(50) null default(''));";//0:not saved; 1:not uploaded; 2:uploaded

	public static final String IsSorting = "IsSorting";
	public static final String CREATE_UnloadingCarInfo = "create table "
			+ TABLE_UnloadingCarInfo + " (" 
			+ ScanID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Cph + "  nvarchar(50) null default(''), "
			+ Driver + "  nvarchar(50) null default(''), "
			+ IsSorting + "  nvarchar(50) null default(''), "
			+ GpsCode + "  nvarchar(50) null default(''), "
			+ StationName + "  nvarchar(50) null default(''), "
			+ TransferPhone + "  nvarchar(50) null default(''), "
			+ ScanTime + "  nvarchar(50) null default(''), "
			+ CrtBillNo + "  nvarchar(50) null default(''), "
			+ Flag + " nvarchar(50) null default(''));";//0:not saved; 1:not uploaded; 2:uploaded


	public static final String CREATE_UnloadingHeader = "create table "
			+ TABLE_UnloadingHeader + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ScanID + "  nvarchar(50) null default(''), "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ OrderDate + "  nvarchar(50) null default(''), "
			+ Status + " nvarchar(50) null default(''));";//0:not saved; 1:not uploaded; 2:uploaded

	public static final String CREATE_UnloadingDetail = "create table "
			+ TABLE_UnloadingDetail + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ScanID + "  nvarchar(50) null default(''), "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ CargoID + "  nvarchar(50) null default(''), "
			+ CargoName + "  nvarchar(50) null default(''), "
			+ Model + "  nvarchar(50) null default(''), "
			+ BatchNo + "  nvarchar(50) null default(''), "
			+ Count + "  nvarchar(50) null default(''), "
			+ Weight + "  nvarchar(50) null default(''), "
			+ Remark + " nvarchar(50) null default(''));";

	public static final String CREATE_UnloadingScan = "create table "
			+ TABLE_UnloadingScan + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ScanID + "  nvarchar(50) null default(''), "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ CargoID + "  nvarchar(50) null default(''), "
			+ Count + "  nvarchar(50) null default(''), "
			+ Remark + "  nvarchar(50) null default(''), "
			+ ScanTime + "  nvarchar(50) null default(''), "
			+ ScanUserID + "  nvarchar(50) null default(''), "
			+ Flag + " nvarchar(50) null default(''));";

	public static final String Consignee = "Consignee";
	public static final String SignOrg = "SignOrg";
	public static final String DeliverStatus = "DeliverStatus";
	public static final String CREATE_PodConsignee = "create table "
			+ TABLE_PodConsignee + " (" 
			+ ScanID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Consignee + "  nvarchar(50) null default(''), "
			+ SignOrg + "  nvarchar(50) null default(''), "
			+ Telephone + "  nvarchar(50) null default(''), "
			+ Cellphone + "  nvarchar(50) null default(''), "
			+ DeliverStatus + "  nvarchar(50) null default(''), "
			+ ScanTime + "  nvarchar(50) null default(''), "
			+ CrtBillNo + "  nvarchar(50) null default(''), "
			+ Flag + " nvarchar(50) null default(''));";

	public static final String CREATE_PodHeader = "create table "
			+ TABLE_PodHeader + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ScanID + "  nvarchar(50) null default(''), "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ OrderDate + "  nvarchar(50) null default(''), "
			+ Status + " nvarchar(50) null default(''));";

	public static final String CREATE_PodDetail = "create table "
			+ TABLE_PodDetail + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ScanID + "  nvarchar(50) null default(''), "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ CargoID + "  nvarchar(50) null default(''), "
			+ CargoName + "  nvarchar(50) null default(''), "
			+ Model + "  nvarchar(50) null default(''), "
			+ BatchNo + "  nvarchar(50) null default(''), "
			+ Count + "  nvarchar(50) null default(''), "
			+ Weight + "  nvarchar(50) null default(''), "
			+ Remark + " nvarchar(50) null default(''));";

	public static final String CREATE_PodScan = "create table "
			+ TABLE_PodScan + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ ScanID + "  nvarchar(50) null default(''), "
			+ OrderID + "  nvarchar(50) null default(''), "
			+ CargoID + "  nvarchar(50) null default(''), "
			+ Count + "  nvarchar(50) null default(''), "
			+ Remark + "  nvarchar(50) null default(''), "
			+ ScanTime + "  nvarchar(50) null default(''), "
			+ ScanUserID + "  nvarchar(50) null default(''), "
			+ Flag + " nvarchar(50) null default(''));";

	public static final String Photo = "Photo";
	public static final String CREATE_ExPhoto = "create table "
			+ TABLE_ExPhoto + " (" 
			+ ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CrtBillNo + "  nvarchar(50) null default(''), "
			+ CargoID + "  nvarchar(50) null default(''), "
			+ Photo + "  nvarchar(50) null default(''), "
			+ ScanTime + "  nvarchar(50) null default(''), "
			+ Flag + " nvarchar(50) null default(''));";

	public DBHelper(Context context) {
		SQLiteDBHelper = new SQLiteDBOpenHelper(context);

	}

	public class SQLiteDBOpenHelper extends SQLiteOpenHelper {

		public SQLiteDBOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			Log.v("upload", "onCreate");

			db.execSQL(CREATE_ExPhoto);
			db.execSQL(CREATE_LoadingCarInfo);
			db.execSQL(CREATE_LoadingHeader);
			db.execSQL(CREATE_LoadingScan);
			db.execSQL(CREATE_LoadingDetail);
			db.execSQL(CREATE_PodConsignee);
			db.execSQL(CREATE_PodHeader);
			db.execSQL(CREATE_PodScan);
			db.execSQL(CREATE_PodDetail);
			db.execSQL(CREATE_PupHeader);
			db.execSQL(CREATE_PupScan);
			db.execSQL(CREATE_PupDetail);
			db.execSQL(CREATE_SysUserRights);
			db.execSQL(CREATE_TABLE_SysCode);
			db.execSQL(CREATE_TABLE_SysUser);
			db.execSQL(CREATE_UnloadingCarInfo);
			db.execSQL(CREATE_UnloadingDetail);
			db.execSQL(CREATE_UnloadingHeader);
			db.execSQL(CREATE_UnloadingScan);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			Log.v("upload", "onUpgrade");
			try {

				//快递公司表增加--修改时间字段
				//				checkColumnExist(db, DBHelper.EXPRESS_TABLE, DBHelper.EXPRESS_MODIFY_TIME);
				//				db.execSQL("DROP TABLE IF EXISTS " + SCANDATA_TABLE);
				onCreate(db);

				if(oldVersion != newVersion) {
					Log.v("zd", "数据库升级成功！");
				} else {
					Log.v("zd", "数据库升级失败！");
				}

			} catch(Exception e) {
				e.printStackTrace();
				Log.v("zd", "数据库升级失败！");
			}
		}

	}

	/**
	 * 方法1：检查某表列是否存在
	 * @param db
	 * @param tableName 表名
	 * @param columnName 列名
	 * @return
	 */
	private void checkColumnExist(SQLiteDatabase db, String tableName, String columnName) {

		boolean result = false ;
		Cursor cursor = null ;
		try{
			cursor = db.rawQuery( "SELECT * FROM " + tableName + " LIMIT 0", null );
			result = cursor != null && cursor.getColumnIndex(columnName) != -1 ;
			Log.v("sql", "快递公司表已存在该字段");
		}catch (Exception e){
			Log.e("sql", e.getMessage()) ;
		}finally{
			if(null != cursor && !cursor.isClosed()){
				cursor.close() ;
			}
		}

		if(result == false){
			addColumn(db, tableName, columnName);
		}
	}

	/**
	 * 数据库升级
	 * 表增加字段
	 * @param db
	 * @param tableName
	 * @param columnName
	 */
	private void addColumn(SQLiteDatabase db, String tableName, String columnName){

		try{

			String sql = "Alter Table " + tableName + " ADD COLUMN " + columnName;
			db.execSQL(sql);
			Log.v("sql", "表增加字段成功");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
