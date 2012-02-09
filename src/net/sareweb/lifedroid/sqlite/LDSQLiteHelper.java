package net.sareweb.lifedroid.sqlite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import net.sareweb.lifedroid.annotation.LDEntity;
import net.sareweb.lifedroid.annotation.LDField;
import net.sareweb.lifedroid.model.LDObject;

import org.apache.commons.lang3.StringUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	
	public T persist(T t){
		if(t.getId()==null){
			long id  = getWritableDatabase().insert(getTableName(), null, composeContentValues(t));
			t.setId(id);
		}
		else{
			String[] ident = new String[] {t.getId().toString()};
			getWritableDatabase().update(getTableName(), composeContentValues(t), "_id=?", ident);
		}
		return t;
	}

	public T getById(Long id){
		String[] ident = new String[] {id.toString()};
		Cursor cur = getReadableDatabase().query(getTableName(), getFieldNames(), "_id=?", ident, null, null, null);
		if(cur==null || cur.getCount()==0) return null;
		cur.moveToFirst();
		Class c = getTypeArgument();
		try {
			Object entityInstance = c.newInstance();
			Field[] fields = c.getFields();
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				Annotation[] annotations = f.getAnnotations();
				if(annotations!=null){
					for (int j = 0; j < annotations.length; j++) {
						Annotation a = annotations[j];
						if(a instanceof LDField){
							setFieldValueFromCursor(entityInstance, f, cur);
							break;
						}
					}
				}
			}
			return (T)entityInstance;
		} catch (Exception e) {
			Log.e(TAG,"Error instantiating class", e);
			return null;
		}
	}
	
	public int delete(T t){
		return delete(t.getId());
	}
	
	public int delete(Long id){
		String[] whereArgs = {id.toString()};
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
	
	
	private void composeCreateSQL() {
		_sqlCreate = "CREATE TABLE " + getTableName() + " (" + getFieldsString() + ")";
	}
	
	private ContentValues composeContentValues(T t) {
		Class c = getTypeArgument();
		Field[] fields = c.getFields();
		ContentValues contentValues = new ContentValues();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			Annotation[] annotations = f.getAnnotations();
			if(annotations!=null){
				for (int j = 0; j < annotations.length; j++) {
					Annotation a = annotations[j];
					if(a instanceof LDField){
						Method m;
						try {
							m = c.getMethod("get" +  StringUtils.capitalize(f.getName()));
						
							if(((LDField) a).id()){
								contentValues.put("_id", (String) m.invoke(t));
							}
							else{
								contentValues.put(f.getName(), (String) m.invoke(t));
							}
						} catch (Exception e) {
							Log.e(TAG, "Error invoking get", e);
						}
					}
				}
			}
		}
		return contentValues;
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
	
	private String[] getFieldNames(){
		Class c = getTypeArgument();
		String[] names= new String[c.getFields().length];
		for (int i = 0; i < c.getFields().length; i++) {
			names[i] = c.getFields()[i].getName();
		}
		return names;
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
	
	private void setFieldValueFromCursor(Object entityInstance, Field field,
			Cursor cur) {
		Method m;
		try {
			
			m = entityInstance.getClass().getMethod("set" +  StringUtils.capitalize(field.getName()), field.getType());
			try {
				if(field.getType().getClass().equals(String.class)){
					m.invoke(entityInstance, cur.getString(cur.getColumnIndex(field.getName())));
				}
				else if(field.getType().getClass().equals(Long.class)){
					m.invoke(entityInstance, cur.getLong(cur.getColumnIndex(field.getName())));
				}
				else if(field.getType().getClass().equals(Double.class)){
					m.invoke(entityInstance, cur.getDouble(cur.getColumnIndex(field.getName())));
				}
			} catch (Exception e) {
				Log.e(TAG, "No method found or security exception", e);
			}
		} catch (Exception e) {
			Log.e(TAG, "No method found or security exception", e);
		}
	}
	

	private String _sqlCreate = "";
	protected String TAG = this.getClass().getName();

}
