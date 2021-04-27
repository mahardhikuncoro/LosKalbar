package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class IndicatorHarga {
    public static final String TABLE                    ="INDICATOR_HARGA";
    public static final String _ID                      ="_ID";
    public static final String BACKEND_ID               ="BACKEND_ID";
    public static final String BATAS_ATAS               ="BATAS_ATAS";
    public static final String BATAS_BAWAH              ="BATAS_BAWAH";
    public static final String INDICATOR_HARGA          ="INDICATOR_HARGA";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +BATAS_ATAS+" REAL, "
                    +BATAS_BAWAH+" REAL, "
                    +INDICATOR_HARGA+" TEXT); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public IndicatorHarga(Context context) {
        this.context = context;
    }

    private IndicatorHarga open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public IndicatorHargaModel save(IndicatorHargaModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(BATAS_ATAS, model.getBatasAtas());
        v.put(BATAS_BAWAH, model.getBatasBawah());
        v.put(INDICATOR_HARGA, model.getIndicator_harga());


        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public IndicatorHargaModel save(Long backendId, Double batasAtas, Double batasBawah, String indicatorHarga) {
        IndicatorHargaModel model = new IndicatorHargaModel();
        model.setBackendId(backendId);
        model.setBatasAtas(batasAtas);
        model.setBatasBawah(batasBawah);
        model.setIndicator_harga(indicatorHarga);

//        Log.e("indicatorHarga", model.getIndicator_harga());
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

    public IndicatorHargaModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        IndicatorHargaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new IndicatorHargaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getDouble((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getDouble((c.getColumnIndex(BATAS_BAWAH))));
                    model.setIndicator_harga(c.getString((c.getColumnIndex(INDICATOR_HARGA))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public IndicatorHargaModel selectBetween(Double value) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (value != null)
            q += " AND "+BATAS_ATAS+">="+value+" AND "+BATAS_BAWAH+"<="+value;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        IndicatorHargaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new IndicatorHargaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getDouble((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getDouble((c.getColumnIndex(BATAS_BAWAH))));
                    model.setIndicator_harga(c.getString((c.getColumnIndex(INDICATOR_HARGA))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public IndicatorHargaModel selectBy(Double batasAtas, Double batasBawah) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((batasAtas != null)&&(batasBawah != null))
            q += " AND "+BATAS_ATAS+"="+batasAtas+" AND "+BATAS_BAWAH+"="+batasBawah;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        IndicatorHargaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new IndicatorHargaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getDouble((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getDouble((c.getColumnIndex(BATAS_BAWAH))));
                    model.setIndicator_harga(c.getString((c.getColumnIndex(INDICATOR_HARGA))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<IndicatorHargaModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<IndicatorHargaModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        IndicatorHargaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new IndicatorHargaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setBatasAtas(c.getDouble((c.getColumnIndex(BATAS_ATAS))));
                    model.setBatasBawah(c.getDouble((c.getColumnIndex(BATAS_BAWAH))));
                    model.setIndicator_harga(c.getString((c.getColumnIndex(INDICATOR_HARGA))));
                    list.add(model);
                } while (c.moveToNext());
            }
        }
        close();
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
