package kim.uno.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.List;

import kim.uno.base.util.LogUtil;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static HashMap<String, DBOpenHelper> mInstance = new HashMap<String, DBOpenHelper>();
	private List<Table> mTables;

	public static DBOpenHelper getInstance(Context context, String name, CursorFactory factory, int version) {
		if(!mInstance.containsKey(name.toLowerCase())){
			DBOpenHelper helper = new DBOpenHelper(context, name, factory, version);
			mInstance.put(name.toLowerCase(), helper);
		}
		return mInstance.get(name.toLowerCase());
	}

	@Deprecated
	public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (Table table : mTables) {
			db.execSQL(table.getCreateQuery());
			LogUtil.i(table.getCreateQuery());
			for(Table.Index index : table.getIndexes()){
				db.execSQL(index.getQuery(table.getName()));
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.beginTransaction();
		try {

            for (Table table : mTables) {
				try { db.execSQL(table.getDropQuery());
				} catch (Exception e) { LogUtil.e(e); }
			}

            onCreate(db);
            db.setTransactionSuccessful();
        } catch (Exception e) { LogUtil.e(e); }
        finally { db.endTransaction(); }
	}

	public void setTables(List<Table> mTables) {
		this.mTables = mTables;
	}

	public List<Table> getTables() {
		return mTables;
	}
	
}
