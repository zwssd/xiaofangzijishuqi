package com.example.david.myapplication;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public int addnumtype = 0;
	public DBAdapter db = new DBAdapter(this);//使用数据库类
	
	public TextView textView1;
	public TextView textView2;
	public TextView textView01;
	public TextView textView02;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView01 = (TextView) findViewById(R.id.TextView01);
        textView02 = (TextView) findViewById(R.id.TextView02);
        
        findViewById(R.id.button2).setOnClickListener(new OnClickListener(){  
            public void onClick(View v) {  
                //Toast.makeText(MainActivity.this, "Toast:Button2", Toast.LENGTH_SHORT).show();
            	db.open(); 
                switch(addnumtype){
                case 1:
                	boolean isok = db.updatenum(1);
                	if(isok){
                		Cursor cursor = db.getmynum();
                		if(cursor.moveToFirst()){
                        	textView1.setText(cursor.getString(cursor.getColumnIndex("dbz")));
                        	}
                	}
                	
                	break;
                case 2:
                	boolean isok2 = db.updatenum(2);
                	if(isok2){
                		Cursor cursor = db.getmynum();
                		if(cursor.moveToFirst()){
                        	textView2.setText(cursor.getString(cursor.getColumnIndex("xj")));
                        	}
                	}
                	break;
                case 3:
                	boolean isok3 = db.updatenum(3);
                	if(isok3){
                		Cursor cursor = db.getmynum();
                		if(cursor.moveToFirst()){
                        	textView01.setText(cursor.getString(cursor.getColumnIndex("wsz")));
                        	}
                	}
                	break;
                case 4:
                	boolean isok4 = db.updatenum(4);
                	if(isok4){
                		Cursor cursor = db.getmynum();
                		if(cursor.moveToFirst()){
                        	textView02.setText(cursor.getString(cursor.getColumnIndex("qf")));
                        	}
                	}
                	break;
                							}
            	db.close();
            }  
        });  
        findViewById(R.id.button1).setOnClickListener(new OnClickListener(){  
            public void onClick(View v) {  
            	refresh();
            	
            }
        });
    }
  
    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            /*
             * 
             * add()方法的四个参数，依次是：
             * 
             * 1、组别，如果不分组的话就写Menu.NONE,
             * 
             * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单
             * 
             * 3、顺序，那个菜单现在在前面由这个参数的大小决定
             * 
             * 4、文本，菜单的显示文本
             */
            menu.add(Menu.NONE, Menu.FIRST + 1, 1, "大悲咒").setIcon(
            android.R.drawable.ic_menu_edit);
            // setIcon()方法为菜单设置图标，这里使用的是系统自带的图标，同学们留意一下,以
            // android.R开头的资源是系统提供的，我们自己提供的资源是以R开头的
            menu.add(Menu.NONE, Menu.FIRST + 2, 2, "心经").setIcon(
            android.R.drawable.ic_menu_edit);
            menu.add(Menu.NONE, Menu.FIRST + 3, 3, "往生咒").setIcon(
            android.R.drawable.ic_menu_edit);
            menu.add(Menu.NONE, Menu.FIRST + 4, 4, "七佛").setIcon(
            android.R.drawable.ic_menu_edit);
            menu.add(Menu.NONE, Menu.FIRST + 5, 5, "计数").setIcon(
            android.R.drawable.ic_menu_delete);
            menu.add(Menu.NONE, Menu.FIRST + 6, 6, "显示记录").setIcon(
            android.R.drawable.ic_menu_help);
            menu.add(Menu.NONE, Menu.FIRST + 7, 7, "初始化").setIcon(
            android.R.drawable.ic_menu_info_details);
            return true;
        }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case Menu.FIRST + 1:
    addnumtype = 1;
    //Toast.makeText(this, "addnumtype="+addnumtype, Toast.LENGTH_LONG).show();
    break;
    case Menu.FIRST + 2:
    	addnumtype = 2;
    //Toast.makeText(this, "addnumtype = "+addnumtype, Toast.LENGTH_LONG).show();
    break;
    case Menu.FIRST + 3:
    	addnumtype = 3;
    //Toast.makeText(this, "addnumtype = "+addnumtype, Toast.LENGTH_LONG).show();
    break;
    case Menu.FIRST + 4:
    	addnumtype = 4;
    //Toast.makeText(this, "addnumtype = "+addnumtype, Toast.LENGTH_LONG).show();
    break;
    case Menu.FIRST + 5:
    	int cleanreturn = 0;
    	db.open();
    	cleanreturn = db.cleannum();
    	db.close();
    	switch(cleanreturn){
    	case 1:
    		refresh();
    		Toast.makeText(this, "数据计数成功了！", Toast.LENGTH_LONG).show();
    		break;
    	case 2:
    		Toast.makeText(this, "念诵数量不够一章小房子！", Toast.LENGTH_LONG).show();
    		break;
    	case 3:
    		Toast.makeText(this, "失败！原因：异常！", Toast.LENGTH_LONG).show();
    		break;
    	}
    	
    break;
    case Menu.FIRST + 6:
    	db.open();
    Cursor cursor = db.getmylog();
    String mylog = "";
    	while(cursor.moveToNext()){
    		mylog += "完成日期："+cursor.getString(cursor.getColumnIndex("mydate"))+"\n";
    	}
    	db.close();
    Toast.makeText(this, mylog, Toast.LENGTH_LONG).show();
    break;
    case Menu.FIRST + 7:
    	db.open();
    	db.myCreate();
    	db.close();
    Toast.makeText(this, "数据初始化成功了！", Toast.LENGTH_LONG).show();
    break;
    }
    return false;
    }
    
    private void refresh(){
    	int dbznum = 0;
    	int xjnum = 0;
    	int wsznum = 0;
    	int qfnum = 0;  	
    	db.open();
    	Cursor cursor = db.getmynum();
    	if(cursor.moveToFirst()){
    		textView1.setText(cursor.getString(cursor.getColumnIndex("dbz")));
    		textView2.setText(cursor.getString(cursor.getColumnIndex("xj")));
    		textView01.setText(cursor.getString(cursor.getColumnIndex("wsz")));
    		textView02.setText(cursor.getString(cursor.getColumnIndex("qf")));
    	}
    	db.close();
    }
}
