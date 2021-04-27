package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ParamKategoriAsuransi {
    public static final String TABLE                ="PARAMETER_KAT_ASURANSI";
    public static final String _ID                  ="_ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String BATAS_ATAS            ="BATAS_ATAS";
    public static final String BATAS_BAWAH            ="BATAS_BAWAH";
    public static final String VALUE                ="VALUE";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +BATAS_ATAS+" INTEGER, "
                    +BATAS_BAWAH+" INTEGER, "
                    +VALUE+" INTEGER); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public ParamKategoriAsuransi(Context context) {
        this.context = context;
    }

    private ParamKategoriAsuransi open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public ParamKategoriAsuransiModel save(ParamKategoriAsuransiModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(BATAS_ATAS, model.getBatasAtas());
        v.put(BATAS_BAWAH, model.getBatasBawah());
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

    public ParamKategoriAsuransiModel save(Long backendId, Integer batasAtas, Integer batasBawah, String value) {
        ParamKategoriAsuransiModel model = new ParamKategoriAsuransiModel();
        model.setBackendId(backendId);
        model.setBatasAtas(batasAtas);
        model.setBatasBawah(batasBawah);
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

    public ParamKategoriAsuransiModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamKategoriAsuransiModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamKategoriAsuransiModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getInt((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getInt((c.getColumnIndex(BATAS_BAWAH))));
                    model.setValue(c.getString((c.getColumnIndex(VALUE))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public ParamKategoriAsuransiModel selectBetween(Integer value) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (value != null)
            q += " AND "+BATAS_ATAS+">="+value+" AND "+BATAS_BAWAH+"<="+value;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamKategoriAsuransiModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamKategoriAsuransiModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getInt((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getInt((c.getColumnIndex(BATAS_BAWAH))));
                    model.setValue(c.getString((c.getColumnIndex(VALUE))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public ParamKategoriAsuransiModel selectBy(Integer batasAtas, Integer batasBawah) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((batasAtas != null)&&(batasBawah != null))
            q += " AND "+BATAS_ATAS+"="+batasAtas+" AND "+BATAS_BAWAH+"="+batasBawah;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamKategoriAsuransiModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamKategoriAsuransiModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getInt((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getInt((c.getColumnIndex(BATAS_BAWAH))));
                    model.setValue(c.getString((c.getColumnIndex(VALUE))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<ParamKategoriAsuransiModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<ParamKategoriAsuransiModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamKategoriAsuransiModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamKategoriAsuransiModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getInt((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getInt((c.getColumnIndex(BATAS_BAWAH))));
                    model.setValue(c.getString((c.getColumnIndex(VALUE))));
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
