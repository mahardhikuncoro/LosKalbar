package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateBunga {
    public static final String TABLE                ="RATE_BUNGA";
    public static final String _ID                  ="_ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String TIPE                 ="TIPE";
    public static final String PRODUK               ="PRODUK";
    public static final String INDICATOR_TAHUN           ="INDICATOR_TAHUN";
    public static final String RATE_TAHUN1         ="RATE_TAHUN1";
    public static final String RATE_TAHUN2         ="RATE_TAHUN2";
    public static final String RATE_TAHUN3         ="RATE_TAHUN3";
    public static final String RATE_TAHUN4         ="RATE_TAHUN4";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +TIPE+" TEXT, "
                    +PRODUK+" TEXT, "
                    +INDICATOR_TAHUN+" TEXT, "
                    +RATE_TAHUN1+" REAL, "
                    +RATE_TAHUN2+" REAL, "
                    +RATE_TAHUN3+" REAL, "
                    +RATE_TAHUN4+" REAL); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public RateBunga(Context context) {
        this.context = context;
    }

    private RateBunga open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public RateBungaModel save(RateBungaModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(TIPE, model.getTipe());
        v.put(PRODUK, model.getProduk());
        v.put(INDICATOR_TAHUN, model.getIndicatorTahun());
        v.put(RATE_TAHUN1, model.getRateTahun1());
        v.put(RATE_TAHUN2, model.getRateTahun2());
        v.put(RATE_TAHUN3, model.getRateTahun3());
        v.put(RATE_TAHUN4, model.getRateTahun4());

        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public RateBungaModel save(Long backendId, String tipe, String produk, String indicatorTahun,
                               Double rateTahun1, Double rateTahun2, Double rateTahun3, Double rateTahun4) {
        RateBungaModel model = new RateBungaModel();
        model.setBackendId(backendId);
        model.setTipe(tipe);
        model.setProduk(produk);
        model.setIndicatorTahun(indicatorTahun);
        model.setRateTahun1(rateTahun1);
        model.setRateTahun2(rateTahun2);
        model.setRateTahun3(rateTahun3);
        model.setRateTahun4(rateTahun4);
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

    public RateBungaModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateBungaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateBungaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setProduk(c.getString((c.getColumnIndex(PRODUK))));
                    model.setIndicatorTahun(c.getString((c.getColumnIndex(INDICATOR_TAHUN))));
                    model.setRateTahun1(c.getDouble((c.getColumnIndex(RATE_TAHUN1))));
                    model.setRateTahun2(c.getDouble((c.getColumnIndex(RATE_TAHUN2))));
                    model.setRateTahun3(c.getDouble((c.getColumnIndex(RATE_TAHUN3))));
                    model.setRateTahun4(c.getDouble((c.getColumnIndex(RATE_TAHUN4))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public RateBungaModel selectBy(String tipe, String produk, String indicatorTahun) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((tipe != null)&&(produk!=null)&&(indicatorTahun!=null))
            q += " AND "+TIPE+" = '"+tipe+"' AND "+PRODUK+" = '"+produk+"' AND "+INDICATOR_TAHUN+" = '"+indicatorTahun+"' ";

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateBungaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateBungaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setProduk(c.getString((c.getColumnIndex(PRODUK))));
                    model.setIndicatorTahun(c.getString((c.getColumnIndex(INDICATOR_TAHUN))));
                    model.setRateTahun1(c.getDouble((c.getColumnIndex(RATE_TAHUN1))));
                    model.setRateTahun2(c.getDouble((c.getColumnIndex(RATE_TAHUN2))));
                    model.setRateTahun3(c.getDouble((c.getColumnIndex(RATE_TAHUN3))));
                    model.setRateTahun4(c.getDouble((c.getColumnIndex(RATE_TAHUN4))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<RateBungaModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<RateBungaModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateBungaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateBungaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setProduk(c.getString((c.getColumnIndex(PRODUK))));
                    model.setIndicatorTahun(c.getString((c.getColumnIndex(INDICATOR_TAHUN))));
                    model.setRateTahun1(c.getDouble((c.getColumnIndex(RATE_TAHUN1))));
                    model.setRateTahun2(c.getDouble((c.getColumnIndex(RATE_TAHUN2))));
                    model.setRateTahun3(c.getDouble((c.getColumnIndex(RATE_TAHUN3))));
                    model.setRateTahun4(c.getDouble((c.getColumnIndex(RATE_TAHUN4))));
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
