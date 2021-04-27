package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ParameterPinjaman {
    public static final String TABLE                ="PARAMETER_PINJAMAN";
    public static final String _ID                  ="_ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String PARAMETER           ="PARAMETER";
    public static final String DESCRIPTION            ="DESCRIPTION";
    public static final String VALUE_MOBIL            ="VALUE_MOBIL";
    public static final String VALUE_MOTOR                ="VALUE_MOTOR";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +PARAMETER+" TEXT, "
                    +DESCRIPTION+" TEXT, "
                    +VALUE_MOBIL+" INTEGER, "
                    +VALUE_MOTOR+" INTEGER); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public ParameterPinjaman(Context context) {
        this.context = context;
    }

    private ParameterPinjaman open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public ParameterPinjamanModel save(ParameterPinjamanModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(DESCRIPTION, model.getDescription());
        v.put(VALUE_MOBIL, model.getValue_mobil());
        v.put(VALUE_MOTOR, model.getValue_motor());
        v.put(PARAMETER, model.getParameter());


        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public ParameterPinjamanModel save(Long backendId, String parameter, String description, Integer valueMobil, Integer valueMotor) {
        ParameterPinjamanModel model = new ParameterPinjamanModel();
        model.setBackendId(backendId);
        model.setDescription(description);
        model.setValue_mobil(valueMobil);
        model.setValue_motor(valueMotor);
        model.setParameter(parameter);

//        Log.e("Parameter", parameter);
//        Log.e("Tahun max mobil", valueMobil.toString());
//        Log.e("Tahun max motor", valueMotor.toString());
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

    public ParameterPinjamanModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParameterPinjamanModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParameterPinjamanModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setDescription(c.getString((c.getColumnIndex(DESCRIPTION))));
                    model.setValue_mobil(c.getInt((c.getColumnIndex(VALUE_MOBIL))));
                    model.setValue_motor(c.getInt((c.getColumnIndex(VALUE_MOTOR))));
                    model.setParameter(c.getString((c.getColumnIndex(PARAMETER))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public ParameterPinjamanModel selectBy(String parameter) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((parameter!=null))
            q +=" AND "+PARAMETER+" = '"+parameter+"' ";

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParameterPinjamanModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParameterPinjamanModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setDescription(c.getString((c.getColumnIndex(DESCRIPTION))));
                    model.setValue_mobil(c.getInt((c.getColumnIndex(VALUE_MOBIL))));
                    model.setValue_motor(c.getInt((c.getColumnIndex(VALUE_MOTOR))));
                    model.setParameter(c.getString((c.getColumnIndex(PARAMETER))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<ParameterPinjamanModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<ParameterPinjamanModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParameterPinjamanModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParameterPinjamanModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setDescription(c.getString((c.getColumnIndex(DESCRIPTION))));
                    model.setValue_mobil(c.getInt((c.getColumnIndex(VALUE_MOBIL))));
                    model.setValue_motor(c.getInt((c.getColumnIndex(VALUE_MOTOR))));
                    model.setParameter(c.getString((c.getColumnIndex(PARAMETER))));
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
