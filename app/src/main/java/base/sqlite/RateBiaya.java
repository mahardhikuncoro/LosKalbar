package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateBiaya {
    public static final String TABLE                ="RATE_BIAYA";
    public static final String _ID                  ="_ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String TIPE                 ="TIPE";
    public static final String KATEGORI             ="KATEGORI";
    public static final String PRODUK               ="PRODUK";
    public static final String NAMA_BIAYA           ="NAMA_BIAYA";
    public static final String TENOR                ="TENOR";
    public static final String VALUE                ="VALUE";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +TIPE+" TEXT, "
                    +KATEGORI+" TEXT, "
                    +PRODUK+" TEXT, "
                    +NAMA_BIAYA+" TEXT, "
                    +TENOR+" INTEGER, "
                    +VALUE+" REAL); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public RateBiaya(Context context) {
        this.context = context;
    }

    private RateBiaya open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public RateBiayaModel save(RateBiayaModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(TIPE, model.getTipe());
        v.put(KATEGORI, model.getKategori());
        v.put(PRODUK, model.getProduk());
        v.put(NAMA_BIAYA, model.getNamaBiaya());
        v.put(TENOR, model.getTenor());
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

    public RateBiayaModel save(Long backendId, String tipe, String kategori, String produk, String namaBiaya,
                               Integer tenor, Double value) {
        RateBiayaModel model = new RateBiayaModel();
        model.setBackendId(backendId);
        model.setTipe(tipe);
        model.setKategori(kategori);
        model.setProduk(produk);
        model.setNamaBiaya(namaBiaya);
        model.setTenor(tenor);
        model.setValue(value);

//        Log.e("Biaya", model.getNamaBiaya());
//        Log.e("Value Biaya", model.getValue().toString());
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

    public RateBiayaModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateBiayaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateBiayaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setProduk(c.getString((c.getColumnIndex(PRODUK))));
                    model.setNamaBiaya(c.getString((c.getColumnIndex(NAMA_BIAYA))));
                    model.setTenor(c.getInt((c.getColumnIndex(TENOR))));
                    model.setValue(c.getDouble((c.getColumnIndex(VALUE))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

//    public List<RateBiayaModel> selectBy(String tipe, String produk,  Integer tenor) {
//        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
//        if ((tipe != null)&&(produk!=null)&&(tenor!=null))
//            q += " AND "+TIPE+" = '"+tipe+"' AND "+PRODUK+" = '"+produk+"' AND "+TENOR+"="+tenor;

    public List<RateBiayaModel> selectBy(String tipe, String kategori, String produk,  Integer tenor) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((tipe != null)&&(kategori!=null)&&(produk!=null)&&(tenor!=null))
            q += " AND "+TIPE+" = '"+tipe+"' AND "+KATEGORI+" = '"+kategori+"' AND "+PRODUK+" = '"+produk+"' AND "+TENOR+"="+tenor;

        List<RateBiayaModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateBiayaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateBiayaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setKategori(c.getString((c.getColumnIndex(KATEGORI))));
                    model.setProduk(c.getString((c.getColumnIndex(PRODUK))));
                    model.setNamaBiaya(c.getString((c.getColumnIndex(NAMA_BIAYA))));
                    model.setTenor(c.getInt((c.getColumnIndex(TENOR))));
                    model.setValue(c.getDouble((c.getColumnIndex(VALUE))));
                    list.add(model);
                } while (c.moveToNext());
            }
        }

        close();
        return list;

        /*open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateBiayaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateBiayaModel();
                    model.setId(c.getInt(c.getColumnIndex(USERID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setProduk(c.getString((c.getColumnIndex(PRODUK))));
                    model.setNamaBiaya(c.getString((c.getColumnIndex(NAMA_BIAYA))));
                    model.setTenor(c.getInt((c.getColumnIndex(TENOR))));
                    model.setValue(c.getDouble((c.getColumnIndex(VALUE))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;*/
    }

    public RateBiayaModel selectType(String nama_biaya) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((nama_biaya!=null))
            q +=" AND "+NAMA_BIAYA+" = '"+nama_biaya+"' ";

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateBiayaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateBiayaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setKategori(c.getString((c.getColumnIndex(KATEGORI))));
                    model.setProduk(c.getString((c.getColumnIndex(PRODUK))));
                    model.setNamaBiaya(c.getString((c.getColumnIndex(NAMA_BIAYA))));
                    model.setTenor(c.getInt((c.getColumnIndex(TENOR))));
                    model.setValue(c.getDouble((c.getColumnIndex(VALUE))));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<RateBiayaModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<RateBiayaModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateBiayaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateBiayaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setKategori(c.getString((c.getColumnIndex(KATEGORI))));
                    model.setProduk(c.getString((c.getColumnIndex(PRODUK))));
                    model.setNamaBiaya(c.getString((c.getColumnIndex(NAMA_BIAYA))));
                    model.setTenor(c.getInt((c.getColumnIndex(TENOR))));
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
