package net.sareweb.lifedroid.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import net.sareweb.lifedroid.model.LDObject;

public abstract class LDSQLiteHelper<T extends LDObject> extends
		SQLiteOpenHelper {

	public LDSQLiteHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		composeCreateSQL();
	}

	private void composeCreateSQL() {
		_sqlCreate = "CREATE TABLE " + T.tableName + "(" + ")";
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(_sqlCreate);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 db.execSQL("DROP TABLE IF EXISTS " + T.tableName);
		 db.execSQL(_sqlCreate);
	}

	private String _sqlCreate = "";
	
	public abstract T persist(T t);
	
	public abstract T getById(String Id);
	
	public int delete(T t){
		return delete(t.id);
	}
	
	public int delete(String id){
		String[] whereArgs = {id};
		return this.getWritableDatabase().delete(T.tableName, "_id=?", whereArgs);
	}

}
