package net.sareweb.lifedroid.sqlite;

import net.sareweb.lifedroid.model.DLFileEntry;
import net.sareweb.lifedroid.sqlite.generic.LDSQLiteHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DLFileEntrySQLiteHelper extends LDSQLiteHelper<DLFileEntry> {

	public DLFileEntrySQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

}
