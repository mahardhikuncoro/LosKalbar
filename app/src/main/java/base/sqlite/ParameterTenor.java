package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ParameterTenor {
    public static final String TABLE                ="PARAMETER_TENOR";
    public static final String _ID                  ="_ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String PARAMETER           ="PARAMETER";
    public static final String BATAS_ATAS            ="BATAS_ATAS";
    public static final String BATAS_BAWAH            ="BATAS_BAWAH";
    public static final String VALUE                ="VALUE";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +PARAMETER+" TEXT, "
                    +BATAS_ATAS+" REAL, "
                    +BATAS_BAWAH+" REAL, "
                    +VALUE+" INTEGER); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public ParameterTenor(Context context) {
        this.context = context;
    }

    private ParameterTenor open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public ParameterTenorModel save(ParameterTenorModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(BATAS_ATAS, model.getBatasAtas());
        v.put(BATAS_BAWAH, model.getBatasBawah());
        v.put(VALUE, model.getValue());
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

    public ParameterTenorModel save(Long backendId, String parameter, Double batasAtas, Double batasBawah, Integer value) {
        ParameterTenorModel model = new ParameterTenorModel();
        model.setBackendId(backendId);
        model.setBatasAtas(batasAtas);
        model.setBatasBawah(batasBawah);
        model.setValue(value);
        model.setParameter(parameter);
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

    public ParameterTenorModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParameterTenorModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParameterTenorModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getDouble((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getDouble((c.getColumnIndex(BATAS_BAWAH))));
                    model.setValue(c.getInt((c.getColumnIndex(VALUE))));
                    model.setParameter(c.getString((c.getColumnIndex(PARAMETER))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public ParameterTenorModel selectBetween(String parameter, Integer value) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((value != null)&&(parameter!=null))
            q += " AND "+BATAS_ATAS+">="+value+" AND "+BATAS_BAWAH+"<="+value+" AND "+PARAMETER+" = '"+parameter+"'";

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParameterTenorModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParameterTenorModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getDouble((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getDouble((c.getColumnIndex(BATAS_BAWAH))));
                    model.setValue(c.getInt((c.getColumnIndex(VALUE))));
                    model.setParameter(c.getString((c.getColumnIndex(PARAMETER))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public ParameterTenorModel selectBy(String parameter, Integer batasAtas, Integer batasBawah) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((batasAtas != null)&&(batasBawah != null)&&(parameter!=null))
            q += " AND "+BATAS_ATAS+"="+batasAtas+" AND "+BATAS_BAWAH+"="+batasBawah+" AND "+PARAMETER+"="+parameter;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParameterTenorModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParameterTenorModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getDouble((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getDouble((c.getColumnIndex(BATAS_BAWAH))));
                    model.setValue(c.getInt((c.getColumnIndex(VALUE))));
                    model.setParameter(c.getString((c.getColumnIndex(PARAMETER))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<ParameterTenorModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<ParameterTenorModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParameterTenorModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParameterTenorModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getDouble((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getDouble((c.getColumnIndex(BATAS_BAWAH))));
                    model.setValue(c.getInt((c.getColumnIndex(VALUE))));
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
