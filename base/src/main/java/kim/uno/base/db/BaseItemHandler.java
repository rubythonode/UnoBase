package kim.uno.base.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import kim.uno.base.item.DBItem;
import kim.uno.base.util.LogUtil;

public class BaseItemHandler<E extends DBItem> {

    private SQLiteDatabase mDatabase;
    private Table mTable;
    private Class<E> mClass;

    public BaseItemHandler(Context context, Table table, Class<E> itemClass) {
        mDatabase = DatabaseAdapter.getInstance(context).getDatabase();
        mTable = table;
        mClass = itemClass;
    }


    /*
     * -------------------------------------------------------------------
     * 기본 메소드
     * -------------------------------------------------------------------
     */

    public List<E> getAllItem() {
        return getAllItem("_ID DESC");
    }

    public List<E> getAllItem(String orderBy) {
        return getAllItem(orderBy, null);
    }

    public List<E> getAllItem(String orderBy, String limit) {
        return query(null, null, null, null, null, orderBy, limit);
    }

    public long getCount() {
        Cursor cursor = queryCursor(new String[] { "count(*)" }, null, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public long insert(E item) {
        return mDatabase.insert(mTable.getName(), null, toContentValues(item));
    }

    public long insert(ArrayList<E> items) {
        mDatabase.beginTransaction();
        try {
            for (E item : items) insert(item);
            mDatabase.setTransactionSuccessful();
        } catch (Exception e) { LogUtil.e(e);
        } finally { mDatabase.endTransaction(); }
        return getCount();
    }

    public long delete(String selection, String[] selectionArgs) {
        return mDatabase.delete(mTable.getName(), selection, selectionArgs);
    }

    public long clear() {
        return mDatabase.delete(mTable.getName(), null, null);
    }


    /*
     * -------------------------------------------------------------------
     * SQL 수행
     * -------------------------------------------------------------------
     */

    public List<E> query(String selection, String limit) {
        return query(null, selection, null, null, null, null, limit);
    }

    public List<E> query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return Matche(queryCursor(columns, selection, selectionArgs, groupBy, having, orderBy, limit));
    }

    public Cursor queryCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return mDatabase.query(mTable.getName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }


    /*
     * -------------------------------------------------------------------
     * RAW SQL 수행
     * -------------------------------------------------------------------
     */

    public List<E> rawQuery(String sql) {
        return rawQuery(sql, null);
    }

    public List<E> rawQuery(String sql, String[] args) {
        return Matche(rawQueryCursor(sql, args));
    }

    public Cursor rawQueryCursor(String sql) {
        return rawQueryCursor(sql, null);
    }

    public Cursor rawQueryCursor(String sql, String[] args) {
        return mDatabase.rawQuery(sql, args);
    }


    /*
     * -------------------------------------------------------------------
     * 객체 매칭
     * -------------------------------------------------------------------
     */

    protected List<E> Matche(Cursor cursor) {
        List<E> Items = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                E item = mClass.newInstance();
                HashMap<String, Object> columns = item.getColumns(cursor);
                item.parse(columns);
                Items.add(item);
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        cursor.close();
        return Items;
    }

    public ContentValues toContentValues(E item) {
        ContentValues values = new ContentValues();
        try {
            JSONObject jsonValue = new JSONObject(item.toString());
            Iterator<String> keys = jsonValue.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                values.put(key, jsonValue.get(key) + "");
            }
        } catch (Exception e) { LogUtil.e(e); }
        return values;
    }

}
