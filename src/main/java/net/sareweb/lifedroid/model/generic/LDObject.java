package net.sareweb.lifedroid.model.generic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sareweb.lifedroid.annotation.LDField;
import net.sareweb.lifedroid.exception.IntrospectionException;
import net.sareweb.lifedroid.sqlite.generic.LDSQLiteHelper;

import org.apache.commons.lang.StringUtils;

import android.util.Log;

public abstract class LDObject<T> {
	
	@LDField
	private String objectStatus = LDSQLiteHelper.OBJECT_STATUS_NEW;

	public Long getId() throws IntrospectionException {
		try {
			Method m = this.getClass().getMethod(
					"get" + StringUtils.capitalize(getIdFieldName()));
			return (Long) m.invoke(this);
		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"Error getting value for ID field", e);
			throw new IntrospectionException();
		}

	}

	public void setId(Long id) throws IntrospectionException {
		try {
			Method m = this.getClass().getMethod(
					"set" + StringUtils.capitalize(getIdFieldName()),
					Long.class);
			m.invoke(this, id);
		} catch (Exception e) {
			throw new IntrospectionException();
		}
	}

	private String getIdFieldName() {

		Class c = this.getClass();

		for (int i = 0; i < c.getDeclaredFields().length; i++) {
			Field f = c.getDeclaredFields()[i];
			Annotation[] annotations = f.getAnnotations();
			if (annotations != null) {
				for (int j = 0; j < annotations.length; j++) {
					Annotation a = annotations[j];
					if (a instanceof LDField) {
						if (((LDField) a).id()) {
							return f.getName();
						}
					}
				}
			}
		}
		return null;
	}

	public String getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(String objectStatus) {
		this.objectStatus = objectStatus;
	}
	
	

}
