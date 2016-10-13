package com.example.david.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class DBAdapter {
	public static final int dbz = 0;
	public static final int xj = 0;
	public static final int wsz = 0;
	public static final int qf = 0;
	public static final int id = 0;
	public static final String date = "mydate";
	private static final String DATABASE_NAME = "nianjing";
	private static final String DATABASE_TABLE = "mynum";
	private static final String DATABASE_TABLE2 = "mylog";
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_CREATE =
			"create table mynum (id integer primary key autoincrement,dbz integer not null, "
			+ "xj integer not null,wsz integer not null,qf integer not null);";
	private static final String DATABASE_CREATE2 =
	"create table mylog (id integer primary key autoincrement, "
	+ "mydate date not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	public DBAdapter(Context ctx)
	{
	this.context = ctx;
	DBHelper = new DatabaseHelper(context);
	}
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
	DatabaseHelper(Context context)
	{
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
		db.execSQL(DATABASE_CREATE2);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);     //删除原来的表
		db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE2);     //删除原来的表
        onCreate(db);                                                      //重新创建删除的表
	}
	}
	//初始化数据库
	public void myCreate()
	{
		//db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
		//db.execSQL(DATABASE_CREATE);
		db.execSQL("insert into "+DATABASE_TABLE+"(id,dbz,xj,wsz,qf)values(0,0,0,0,0)");
		//db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE2);
		//db.execSQL(DATABASE_CREATE2);
	}
	//---打开数据库---

	public DBAdapter open() throws SQLException
	{
	db = DBHelper.getWritableDatabase();
	return this;
	}
	//---关闭数据库---

	public void close()
	{
	DBHelper.close();
	}
	//---向数据库中插入一条完成记录---

	public boolean insertlog()
	{
	//ContentValues initialValues = new ContentValues();
	//initialValues.put(date, date());
		try {
			String mysql = "insert into "+DATABASE_TABLE2+"(mydate)values(date('now'))";
			db.execSQL(mysql);
			return true;
		} catch (SQLException ex) {
			return false;
		}
	//return db.insert(DATABASE_TABLE2, null, initialValues);
	}

	//---检索所有标题---

	public Cursor getAllTitles()
	{
	return db.query(DATABASE_TABLE2, new String[] {
			date},
	null,
	null,
	null,
	null,
	null);
	}
	
	//读取全部计数
	public Cursor getmynum()
	{
		//String mysql = "update "+DATABASE_TABLE+" set dbz=100,xj=100,wsz=100,qf=100 where id=0";
		//db.execSQL(mysql);
		return db.rawQuery("select * from "+DATABASE_TABLE+" where id=0",null);
	}
	
	//读取全部完成时间
		public Cursor getmylog()
		{
			return db.rawQuery("select * from "+DATABASE_TABLE2,null);
		}
	
	//---更新一个计数---

	public boolean updatenum(int addnumtype)
	{
		String mysqlset = "";
	switch(addnumtype){
	case 1:
		mysqlset = "dbz=dbz+1";
		break;
	case 2:
		mysqlset = "xj=xj+1";
		break;
	case 3:
		mysqlset = "wsz=wsz+1";
		break;
	case 4:
		mysqlset = "qf=qf+1";
		break;
	}
	try {
		String mysql = "update "+DATABASE_TABLE+" set "+mysqlset+" where id=0";
		db.execSQL(mysql);
		return true;
	} catch (SQLException ex) {
		return false;
	}
	/*ContentValues args = new ContentValues();
	args.put(KEY_ISBN, isbn);
	args.put(KEY_TITLE, title);
	args.put(KEY_PUBLISHER, publisher);
	return db.update(DATABASE_TABLE, args,
	KEY_ROWID + "=" + rowId, null) > 0;*/
	}
	
	//---清空计数---

		public int cleannum()
		{
			try {
				int dbz = 0;
            	int xj = 0;
            	int wsz = 0;
            	int qf = 0;
				Cursor cursor = this.getmynum();
            	if(cursor.moveToFirst()){
            	dbz = cursor.getInt(cursor.getColumnIndex("dbz"));
            	xj = cursor.getInt(cursor.getColumnIndex("xj"));
            	wsz = cursor.getInt(cursor.getColumnIndex("wsz"));
            	qf = cursor.getInt(cursor.getColumnIndex("qf"));
            	
            	}
            	if(dbz>26 && xj>48 && wsz>83 && qf>86){
            		String mysqlupdate = "update "+DATABASE_TABLE+" set dbz=dbz-27,xj=xj-49,wsz=wsz-84,qf=qf-87 where id=0";
    				db.execSQL(mysqlupdate);
    				insertlog();
    				return 1;//返回成功！
            	}else{
            		return 2;//返回失败，原因：念诵数量不够
            	}
			} catch (SQLException ex) {
				return 3;//返回失败，原因：异常
			}
		}
}
