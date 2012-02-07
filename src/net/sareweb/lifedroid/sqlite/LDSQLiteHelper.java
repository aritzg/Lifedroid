package net.sareweb.lifedroid.sqlite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import net.sareweb.lifedroid.annotation.LDEntity;
import net.sareweb.lifedroid.annotation.LDField;
import net.sareweb.lifedroid.model.LDObject;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public abstract class LDSQLiteHelper<T extends LDObject> extends
		SQLiteOpenHelper {
	
	public LDSQLiteHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}

	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "Creating DB");
		composeCreateSQL();
		Log.d(TAG, "\tCreate sentence: " + _sqlCreate);
		db.execSQL(_sqlCreate);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrading DB"); 
		composeCreateSQL();
		Log.d(TAG, "\tCreate sentence: " + _sqlCreate);
		db.execSQL(_sqlCreate);
	}
	
	private void composeCreateSQL() {
		_sqlCreate = "CREATE TABLE " + getTableName() + " (" + getFieldsString() + ")";
	}
	
	public abstract T persist(T t);
	
	public abstract T getById(String Id);
	
	public int delete(T t){
		return delete(t.id);
	}
	
	public int delete(String id){
		String[] whereArgs = {id};
		return this.getWritableDatabase().delete(getTableName(), "_id=?", whereArgs);
	}
	
	private String getTableName(){
		Class c = getTypeArgument();
		String tName = getAnnotatedTableName();
		if(!"".equals(tName))return tName;
		return c.getSimpleName().toUpperCase();
	}
	
	private String getAnnotatedTableName(){
		Class c = getTypeArgument();
		Annotation[] annotations = c.getAnnotations();
		if(annotations!=null){
			for (int i = 0; i < annotations.length; i++) {
				Annotation a = annotations[i];
				if(a instanceof LDEntity){
					return ((LDEntity)a).tableName().toUpperCase();
				}
			}
		}
		return "";
	}
	
	private String getFieldsString(){
		
		Class c = getTypeArgument();
		
		Field[] fields =c.getFields();
		String fieldsString ="";
		boolean firstField = true;
		for (int i =0 ; i< fields.length; i++){
			String sQLFieldDefinition = composeSQLFieldDefinition(fields[i]);
			if(sQLFieldDefinition!=null && !sQLFieldDefinition.equals("")){
				if(firstField){
					fieldsString = fieldsString + sQLFieldDefinition;
					firstField=false;
				}
				else{
					fieldsString = fieldsString + ", " + sQLFieldDefinition;
				}
			}
		}
		return fieldsString;
	}
	
	private Class<T> getTypeArgument(){
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class)parameterizedType.getActualTypeArguments()[0];
	}
	
	private String composeSQLFieldDefinition(Field f){
		String sQLFieldDefinition ="";
		Annotation[] annotations = f.getAnnotations();
		if(annotations!=null){
			for (int i = 0; i < annotations.length; i++) {
				Annotation a = annotations[i];
				if(a instanceof LDField){
					LDField ldFiledAnnotation = (LDField)a;
					if(ldFiledAnnotation.id()){
						sQLFieldDefinition = "_id " + ldFiledAnnotation.sqliteType() + "  primary key autoincrement";
					}
					else {
						sQLFieldDefinition = f.getName() + " " + ldFiledAnnotation.sqliteType();
					}
				}
			}
		}
		return sQLFieldDefinition;
	}

	private String _sqlCreate = "";
	protected String TAG = this.getClass().getName();

}
