package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateAsuransi {
    public static final String TABLE                ="RATE_ASURANSI";
    public static final String _ID                  ="_ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String TIPE                 ="TIPE";
    public static final String INDICATOR_HARGA      ="INDICATOR_HARGA";
    public static final String KATEGORI_ASURANSI    ="KATEGORI_ASURANSI";
    public static final String RATE_WILAYAH1        ="RATE_WILAYAH1";
    public static final String RATE_WILAYAH2        ="RATE_WILAYAH2";
    public static final String RATE_WILAYAH3        ="RATE_WILAYAH3";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +TIPE+" TEXT, "
                    +INDICATOR_HARGA+" TEXT, "
                    +KATEGORI_ASURANSI+" TEXT, "
                    +RATE_WILAYAH1+" REAL, "
                    +RATE_WILAYAH2+" REAL, "
                    +RATE_WILAYAH3+" REAL); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public RateAsuransi(Context context) {
        this.context = context;
    }

    private RateAsuransi open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public RateAsuransiModel save(RateAsuransiModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(TIPE, model.getTipe());
        v.put(INDICATOR_HARGA, model.getIndicatorHarga());
        v.put(KATEGORI_ASURANSI, model.getKategoriAsuransi());
        v.put(RATE_WILAYAH1, model.getRateWilayah1());
        v.put(RATE_WILAYAH2, model.getRateWilayah2());
        v.put(RATE_WILAYAH3, model.getRateWilayah3());

        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public RateAsuransiModel save(Long backendId, String tipe, String indicatorHarga, String kategoriAsuransi,
                                  Double rateWilayah1, Double rateWilayah2, Double rateWilayah3) {
        RateAsuransiModel model = new RateAsuransiModel();
        model.setBackendId(backendId);
        model.setTipe(tipe);
        model.setIndicatorHarga(indicatorHarga);
        model.setKategoriAsuransi(kategoriAsuransi);
        model.setRateWilayah1(rateWilayah1);
        model.setRateWilayah2(rateWilayah2);
        model.setRateWilayah3(rateWilayah3);
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

    public RateAsuransiModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setIndicatorHarga(c.getString((c.getColumnIndex(INDICATOR_HARGA))));
                    model.setKategoriAsuransi(c.getString((c.getColumnIndex(KATEGORI_ASURANSI))));
                    model.setRateWilayah1(c.getDouble((c.getColumnIndex(RATE_WILAYAH1))));
                    model.setRateWilayah2(c.getDouble((c.getColumnIndex(RATE_WILAYAH2))));
                    model.setRateWilayah3(c.getDouble((c.getColumnIndex(RATE_WILAYAH3))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public RateAsuransiModel selectBy(String tipe, String indicatorHarga, String kategoriAsuransi) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((tipe != null)&&(indicatorHarga!=null)&&(kategoriAsuransi!=null))
            q += " AND "+TIPE+" = '"+tipe+"' AND "+INDICATOR_HARGA+" = '"+indicatorHarga+"' AND "+KATEGORI_ASURANSI+" = '"+kategoriAsuransi+"'";

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setIndicatorHarga(c.getString((c.getColumnIndex(INDICATOR_HARGA))));
                    model.setKategoriAsuransi(c.getString((c.getColumnIndex(KATEGORI_ASURANSI))));
                    model.setRateWilayah1(c.getDouble((c.getColumnIndex(RATE_WILAYAH1))));
                    model.setRateWilayah2(c.getDouble((c.getColumnIndex(RATE_WILAYAH2))));
                    model.setRateWilayah3(c.getDouble((c.getColumnIndex(RATE_WILAYAH3))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<RateAsuransiModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<RateAsuransiModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setIndicatorHarga(c.getString((c.getColumnIndex(INDICATOR_HARGA))));
                    model.setKategoriAsuransi(c.getString((c.getColumnIndex(KATEGORI_ASURANSI))));
                    model.setRateWilayah1(c.getDouble((c.getColumnIndex(RATE_WILAYAH1))));
                    model.setRateWilayah2(c.getDouble((c.getColumnIndex(RATE_WILAYAH2))));
                    model.setRateWilayah3(c.getDouble((c.getColumnIndex(RATE_WILAYAH3))));
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
