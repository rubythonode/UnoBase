package kim.uno.item;

import android.database.Cursor;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import kim.uno.util.LogUtil;

public abstract class DBItem implements Serializable {

    @SerializedName("_ID") public String _id;

    public DBItem() { }

    public void CursorMatche(Cursor cursor) {
        HashMap<String, Object> columns = getColumns(cursor);
        parse(columns);
    }

    public HashMap<String, Object> getColumns(Cursor cursor) {
        HashMap<String, Object> columns = new HashMap<String, Object>();
        String[] columnNames = cursor.getColumnNames();
        for (int i = 0; i < columnNames.length; i++)
            columns.put(columnNames[i], cursor.getString(i));
        return columns;
    }

    protected static String getString(HashMap<String, Object> columns, String columnName) {
        return columns.containsKey(columnName) && columns.get(columnName) != null ? String.valueOf(columns.get(columnName)) : null;
    }

    protected static double getDouble(HashMap<String, Object> columns, String columnName) {
        String value = getString(columns, columnName);
        return value != null ? Double.valueOf(value) : 0.0;
    }

    protected static int getInteger(HashMap<String, Object> columns, String columnName) {
        String value = getString(columns, columnName);
        return value != null ? Integer.valueOf(value) : 0;
    }

    protected static long getLong(HashMap<String, Object> columns, String columnName) {
        String value = getString(columns, columnName);
        return value != null ? Long.valueOf(value) : 0;
    }

    protected static boolean getBoolean(HashMap<String, Object> columns, String columnName) {
        String value = getString(columns, columnName);
        return value != null ? value.toUpperCase().equals("TRUE") : false;
    }

    protected static Date getDate(HashMap<String, Object> columns, String columnName) {
        return getDate(columns, columnName, "HH:mm:ss");
    }

    protected static Date getDate(HashMap<String, Object> columns, String columnName, String inputFormat) {
        String value = getString(columns, columnName);
        if (value != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat);
                return dateFormat.parse(value);
            } catch (Exception e) { LogUtil.e(e); }
        }

        return null;
    }

    public void parse(HashMap<String, Object> columns) {
        _id = getString(columns, "_ID");
    }

    @Override
    public String toString() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create().toJson(this);
    }
}