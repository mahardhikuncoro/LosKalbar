package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ParamPenyusutan {
    public static final String TABLE                ="PARAMETER_PENYUSUTAN";
    public static final String _ID                  ="_ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String PARAMETER                 ="PARAMETER";
    public static final String VALUE               ="VALUE";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +PARAMETER+" INTEGER, "
                    +VALUE+" REAL); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public ParamPenyusutan(Context context) {
        this.context = context;
    }

    private ParamPenyusutan open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public ParamPenyusutanModel save(ParamPenyusutanModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(PARAMETER, model.getParameter());
        v.put(VALUE, model.getValue());


        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public ParamPenyusutanModel save(Long backendId, Integer parameter, Double value) {
        ParamPenyusutanModel model = new ParamPenyusutanModel();
        model.setBackendId(backendId);
        model.setParameter(parameter);
        model.setValue(value);
        return save(model);
    }

    public void delete(Integer id) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, _ID + "=?", new String[]{String.valueOf(id)});
        close();
    }

    public void deleteAll() {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();
        sqLiteDatabase.delete(TABLE,null, null);
        close();
    }

    public ParamPenyusutanModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamPenyusutanModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamPenyusutanModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setParameter(c.getInt((c.getColumnIndex(PARAMETER))));
                    model.setValue(c.getDouble((c.getColumnIndex(VALUE))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public ParamPenyusutanModel selectBy(Integer value) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (value != null)
            q += " AND "+PARAMETER+" = '"+value+"' ";

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamPenyusutanModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamPenyusutanModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setParameter(c.getInt((c.getColumnIndex(PARAMETER))));
                    model.setValue(c.getDouble((c.getColumnIndex(VALUE))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<ParamPenyusutanModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<ParamPenyusutanModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamPenyusutanModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamPenyusutanModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setParameter(c.getInt((c.getColumnIndex(PARAMETER))));
                    model.setValue(c.getDouble((c.getColumnIndex(VALUE))));
                    list.add(model);
                } while (c.moveToNext());
            }
        }
        close();
        return list;
    }

    public int count() {
        String q = "SELECT * FROM "+TABLE+" ";
        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);
        int count = c.getCount();
        c.close();
        close();
        return count;
    }
}
