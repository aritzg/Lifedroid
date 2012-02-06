package net.sareweb.lifedroid.sqlite;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import net.sareweb.lifedroid.model.LDObject;


public abstract class LDSQLiteHelper<T extends LDObject> extends
		SQLiteOpenHelper {
	
	public LDSQLiteHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}

	public void onCreate(SQLiteDatabase db) {
		composeCreateSQL();
		db.execSQL(_sqlCreate);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 db.execSQL("DROP TABLE IF EXISTS " + T.tableName);
		 db.execSQL(_sqlCreate);
	}
	
	private void composeCreateSQL() {
		_sqlCreate = "CREATE TABLE " + T.tableName + "(" + getFieldsString() + ")";
	}
	
	public abstract T persist(T t);
	
	public abstract T getById(String Id);
	
	public int delete(T t){
		return delete(t.id);
	}
	
	public int delete(String id){
		String[] whereArgs = {id};
		return this.getWritableDatabase().delete(T.tableName, "_id=?", whereArgs);
	}
	
	private String getFieldsString(){
	     //getClass().getTypeParameters()[0].getClass().getFields();
		String fields ="";
		for (int i =0 ; i< T.fields.length; i++){
			if(i==0) fields = T.fields[i];
			else fields = fields + ", " + T.fields[i];
		}
		return fields;
	}

	private String _sqlCreate = "";

}
