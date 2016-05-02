package kim.uno.base.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.List;

public class DatabaseAdapter {

	private static DatabaseAdapter mInstance;
	private final DBOpenHelper mHelper;
	private final SQLiteDatabase mDatabase;

	public static DatabaseAdapter getInstance(Context context) {
		if (mInstance == null) mInstance = new DatabaseAdapter(context);
		return mInstance;
	}

	@Deprecated
	public DatabaseAdapter(Context context) {
		mHelper = DBOpenHelper.getInstance(context, getName(), null, getVersion());
		mHelper.setTables(getTables());
		mDatabase = mHelper.getWritableDatabase();
	}

	public SQLiteDatabase getDatabase() {
		return mDatabase;
	}

    public static boolean existsDatabase(Context context, String dbName) {
        File dbDir = new File(String.format("/data/data/%s/databases/%s", context.getPackageName(), dbName));
        return dbDir.exists();
    }

	public String getName() {
		return "uno";
	}

	public int getVersion() {
		return 1;
	}

	public List getTables() {
		return null;
	}

}
