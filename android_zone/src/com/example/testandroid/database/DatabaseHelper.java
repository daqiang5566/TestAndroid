package com.example.testandroid.database;

import java.lang.reflect.Field;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.testandroid.database.annotation.Column;
import com.example.testandroid.database.annotation.ColumnType;
import com.example.testandroid.database.annotation.Id;
import com.example.testandroid.database.annotation.Table;
import com.example.testandroid.database.entity.BaseEntity;

public class DatabaseHelper {
	
	private static final String Tag = DatabaseHelper.class.getSimpleName();	
    public  static final String DATABASE_NAME = "testDb"; 
    public  static final int DATABASE_VERSION = 1;	
	private volatile static DatabaseHelper _instance;	
	private SQLiteDatabase mDb;
	private InnerDatabaseHelper sqlHelper;
	
	public static DatabaseHelper getInstance(Context context, long userId) {  
	      if ( _instance == null ) {  
	          synchronized ( DatabaseHelper.class ) {  
	          if (_instance == null) {  
	        	  _instance = new DatabaseHelper(context, userId);
	          }  
	        }  
	     }  
	     return _instance;
	}
	
	public static void InitdateBase(Context context, long userId){
		if(userId != 0){
			getInstance(context,userId);
		}	
	}
	
	private DatabaseHelper (Context context, long userId){
		sqlHelper = new InnerDatabaseHelper(context, userId); 
	}
		
	public SQLiteDatabase open() throws SQLException {
    	if( mDb == null ){
    		synchronized ( DatabaseHelper.class ) {  
    			if( sqlHelper != null){    				
    				this.mDb = this.sqlHelper.getWritableDatabase();  				 				
    			}
    		}
    	}
        return this.mDb;
	}
	
    public void close() {
//    	this.sqlHelper.close();
    }
    
	public static void clear(){
		if (_instance != null) {  
			_instance.mDb.close();
			_instance.mDb = null;
			_instance.sqlHelper.close();
			_instance.sqlHelper = null;
			_instance = null;
		}
	}
    
    public SQLiteDatabase getMdb(){
    	return this.mDb;
    }
	
    private static class InnerDatabaseHelper extends SQLiteOpenHelper {
    	
    	private final String Tag = "InnerDatabaseHelper"; 
    	
    	InnerDatabaseHelper(Context context, long userId) {
            super(context, DATABASE_NAME+userId, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {      
        	
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	Log.i(Tag, "onUpgrade was invoked" + "oldVersion = " + oldVersion + "newVersion = " + newVersion);
        }
        
        private static String genCreateSQL(Class<? extends BaseEntity> entityClass){
        	StringBuilder sql = new StringBuilder();
        	sql.append("create table ");
        	Table tbClz = entityClass.getAnnotation(Table.class);
        	if(tbClz==null){
        		throw new NullPointerException("Table name not defined");
        	}
        	sql.append(tbClz.name());
        	sql.append(" (");
        	Field[] declaredFields = entityClass.getDeclaredFields();  
        	for (Field field : declaredFields) {  
        		field.setAccessible(true);  
        		Column columnAno = field.getAnnotation(Column.class);  
        		Id idAno = field.getAnnotation(Id.class);  
        		if(columnAno != null){
        			sql.append(columnAno.name());
        			ColumnType type = columnAno.type();
        			if(type==ColumnType.INTEGER){
        				sql.append(" INTEGER");
        			}else if(type==ColumnType.REAL){
        				sql.append(" REAL");
        			}else if(type==ColumnType.TEXT){
        				sql.append(" TEXT");
        			}	
        			if(idAno != null){
        				sql.append(" primary key");
        				if(idAno.autoIncrement()){
        					sql.append(" AUTOINCREMENT NOT NULL");
        				}
        			}
        			sql.append(",");
        		}
        	}
        	sql.deleteCharAt(sql.length()-1);
        	sql.append(");");        	
        	Log.i("sql", "sql = " + sql.toString());       	
        	return sql.toString();
        }        
    }
}
