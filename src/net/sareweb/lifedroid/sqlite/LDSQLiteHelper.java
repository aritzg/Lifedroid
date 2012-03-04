package net.sareweb.lifedroid.sqlite;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import net.sareweb.lifedroid.annotation.LDEntity;
import net.sareweb.lifedroid.annotation.LDField;
import net.sareweb.lifedroid.exception.IntrospectionException;
import net.sareweb.lifedroid.model.LDObject;

import org.apache.commons.lang3.ArrayUtils;
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
		getWritableDatabase().close();
	}

	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "Creating DB");
		composeCreateSQL();
		Log.d(TAG, "\tCreate sentence: " + _sqlCreate);
		db.execSQL(_sqlCreate);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + getTableName());
		Log.d(TAG, "Upgrading DB"); 
		composeCreateSQL();
		Log.d(TAG, "\tCreate sentence: " + _sqlCreate);
		db.execSQL(_sqlCreate);
	}
	
	public T persist(T t) throws IntrospectionException{
		if(t.getId()==null){
			long id  = getWritableDatabase().insert(getTableName(), null, composeContentValues(t, false));
			t.setId(id);
		}
		else{
			Log.d(TAG, "getIdFiledName() " + getIdFieldName());
			String[] ident = new String[] {getIdFieldName()};
			getWritableDatabase().update(getTableName(), composeContentValues(t, true), getIdFieldName()+"=?", ident);
		}
		return t;
	}

	public T getById(Long id){
		String[] ident = new String[] {id.toString()};
		Cursor cur = getReadableDatabase().query(getTableName(), getFieldNames(), getIdFieldName()+"=?", ident, null, null, null);
		if(cur==null || cur.getCount()==0){
			Log.d(TAG,"Object not found");
			return null;
		}
		cur.moveToFirst();
		return getObjectFromCursor(cur);
	}
	
	public List<T> query(String selection, String[] selectionArgs){
		Cursor c = getWritableDatabase().query(getTableName(), getFieldNames(), selection, selectionArgs, null, null, null);
		if(c==null)return null;
		ArrayList<T> results = new ArrayList<T>(c.getCount()); 
		
		while(c.moveToNext()){
			results.add(getObjectFromCursor(c));
			
		}
		return results;
	}
	
	
	public int delete(Long id){
		String[] whereArgs = {id.toString()};
		return this.getWritableDatabase().delete(getTableName(), getIdFieldName()+"=?", whereArgs);
	}
	
	protected String getIdFieldName(){
		Class c = getTypeArgument();
		for (int i = 0; i < c.getDeclaredFields().length; i++) {
			Field f = c.getDeclaredFields()[i];
			Annotation[] annotations = f.getAnnotations();
			if(annotations!=null){
				for (int j = 0; j < annotations.length; j++) {
					Annotation a = annotations[j];
					if(a instanceof LDField){
						if(((LDField) a).id()){
							return f.getName();
						}
					}
				}
			}
		}
		return null;
	}
	
	protected String getTableName(){
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
	
	private ContentValues composeContentValues(T t, boolean getIdToo) {
		Class c = getTypeArgument();
		Class superClass = c.getSuperclass();
		
		Field[] fields = c.getDeclaredFields();
		Field[] superFields = superClass.getDeclaredFields();
		Field[] allFields = ArrayUtils.addAll(fields, superFields);
		
		ContentValues contentValues = new ContentValues();
		for (int i = 0; i < allFields.length; i++) {
			Field f = allFields[i];
			Annotation[] annotations = f.getAnnotations();
			if(annotations!=null){
				for (int j = 0; j < annotations.length; j++) {
					Annotation a = annotations[j];
					if(a instanceof LDField){
						
						if(getIdToo==true || !((LDField) a).id()){
							Method m;
							try {
								m = c.getMethod("get" +  StringUtils.capitalize(f.getName()));
								if(m.invoke(t)!=null)contentValues.put(f.getName(), m.invoke(t).toString());
								else contentValues.put(f.getName(), "");
							} 
							catch (Exception e) {
								Log.e(TAG, "Error invoking get for field " + StringUtils.capitalize(f.getName()), e);
							}
						}
					}
				}
			}
		}
		return contentValues;
	}
	
	private String getFieldsString(){
		
		Class c = getTypeArgument();
		Class superClass = c.getSuperclass();
		
		Field[] fields = c.getDeclaredFields();
		Field[] superFields = superClass.getDeclaredFields();
		Field[] allFields = ArrayUtils.addAll(fields, superFields);
		
		String fieldsString ="";
		boolean firstField = true;
		for (int i =0 ; i< allFields.length; i++){
			String sQLFieldDefinition = composeSQLFieldDefinition(allFields[i]);
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
	
	protected String[] getFieldNames(){
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
						sQLFieldDefinition = f.getName() + " " + ldFiledAnnotation.sqliteType() + "  primary key autoincrement";
					}
					else {
						sQLFieldDefinition = f.getName() + " " + ldFiledAnnotation.sqliteType();
					}
				}
			}
		}
		return sQLFieldDefinition;
	}
	
	protected T getObjectFromCursor(Cursor cur){
		Class c = getTypeArgument();
		try {
			Object entityInstance = c.newInstance();
			Field[] fields = c.getDeclaredFields();
			
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				Annotation[] annotations = f.getAnnotations();
				if(annotations!=null){
					for (int j = 0; j < annotations.length; j++) {
						Annotation a = annotations[j];
						if(a instanceof LDField){
							setFieldValueFromCursor(entityInstance, f, cur, ((LDField)a).sqliteType());
							break;
						}
					}
				}
			}
			Log.d(TAG,"Object populated");
			return (T)entityInstance;
		} catch (Exception e) {
			Log.e(TAG,"Error instantiating class", e);
			return null;
		}
	}
	
	private void setFieldValueFromCursor(Object entityInstance, Field field,
			Cursor cur, String sqliteType) {
		Method m;
		try {
			m = entityInstance.getClass().getMethod("set" +  StringUtils.capitalize(field.getName()), field.getType());
			try {
				if(sqliteType.equals(LDField.SQLITE_TYPE_TEXT)){
					m.invoke(entityInstance, cur.getString(cur.getColumnIndex(field.getName())));
				}
				else if(sqliteType.equals(LDField.SQLITE_TYPE_INTEGER)){
					m.invoke(entityInstance, cur.getLong(cur.getColumnIndex(field.getName())));
				}
				else if(sqliteType.equals(LDField.SQLITE_TYPE_REAL)){
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
