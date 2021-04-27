package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ParamPinjamanExtended {
    public static final String TABLE                    ="PARAMETER_PINJAMAN_EXTENDED";
    public static final String _ID                      ="_ID";
    public static final String BACKEND_ID               ="BACKEND_ID";
    public static final String PRODUK                   ="PRODUK";
    public static final String TYPE                     ="TYPE";
    public static final String NILAI                    ="NILAI";
    public static final String SATUAN_NILAI             ="SATUAN_NILAI";
    public static final String USIA_BATAS_BAWAH         ="USIA_BATAS_BAWAH";
    public static final String USIA_BATAS_ATAS          ="USIA_BATAS_ATAS";
    public static final String KETERANGAN               ="KETERANGAN";


    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +PRODUK+" TEXT, "
                    +TYPE+" TEXT, "
                    +NILAI+" INTEGER, "
                    +SATUAN_NILAI+" TEXT, "
                    +USIA_BATAS_BAWAH+" INTEGER, "
                    +USIA_BATAS_ATAS+" INTEGER, "
                    +KETERANGAN+" TEXT); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public ParamPinjamanExtended(Context context) {
        this.context = context;
    }

    private ParamPinjamanExtended open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public ParamPinjamanExtendedModel save(ParamPinjamanExtendedModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(PRODUK, model.getProduk());
        v.put(TYPE, model.getType());
        v.put(NILAI, model.getNilai());
        v.put(SATUAN_NILAI, model.getSatuan_nilai());
        v.put(USIA_BATAS_BAWAH, model.getUsia_batas_bawah());
        v.put(USIA_BATAS_ATAS, model.getUsia_batas_atas());
        v.put(KETERANGAN, model.getKeterangan());


        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public ParamPinjamanExtendedModel save(Long backendId, String produk, String type, Integer nilai,
                                           String satuanNilai, Integer batasBawah, Integer batasAtas,
                                           String keterangan) {
        ParamPinjamanExtendedModel model = new ParamPinjamanExtendedModel();
        model.setBackendId(backendId);
        model.setProduk(produk);
        model.setType(type);
        model.setNilai(nilai);
        model.setSatuan_nilai(satuanNilai);
        model.setUsia_batas_bawah(batasBawah);
        model.setUsia_batas_atas(batasAtas);
        model.setKeterangan(keterangan);

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

    public ParamPinjamanExtendedModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamPinjamanExtendedModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamPinjamanExtendedModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setProduk(c.getString(c.getColumnIndex(PRODUK)));
                    model.setType(c.getString(c.getColumnIndex(TYPE)));
                    model.setNilai(c.getInt(c.getColumnIndex(NILAI)));
                    model.setSatuan_nilai(c.getString(c.getColumnIndex(SATUAN_NILAI)));
                    model.setUsia_batas_bawah(c.getInt(c.getColumnIndex(USIA_BATAS_BAWAH)));
                    model.setUsia_batas_atas(c.getInt(c.getColumnIndex(USIA_BATAS_ATAS)));
                    model.setKeterangan(c.getString(c.getColumnIndex(KETERANGAN)));

                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public ParamPinjamanExtendedModel selectBy(String produk, String type, Integer value) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((produk!=null)&&(type!=null)&&(value!=null));
            q +=" AND "+PRODUK+" = '"+produk+"' AND "+TYPE+" = '"+type+"' AND "+USIA_BATAS_ATAS+">="+value+" AND "+USIA_BATAS_BAWAH+"<="+value;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamPinjamanExtendedModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamPinjamanExtendedModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setProduk(c.getString(c.getColumnIndex(PRODUK)));
                    model.setType(c.getString(c.getColumnIndex(TYPE)));
                    model.setNilai(c.getInt(c.getColumnIndex(NILAI)));
                    model.setSatuan_nilai(c.getString(c.getColumnIndex(SATUAN_NILAI)));
                    model.setUsia_batas_bawah(c.getInt(c.getColumnIndex(USIA_BATAS_BAWAH)));
                    model.setUsia_batas_atas(c.getInt(c.getColumnIndex(USIA_BATAS_ATAS)));
                    model.setKeterangan(c.getString(c.getColumnIndex(KETERANGAN)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<ParamPinjamanExtendedModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<ParamPinjamanExtendedModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        ParamPinjamanExtendedModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new ParamPinjamanExtendedModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getLong(c.getColumnIndex(BACKEND_ID)));
                    model.setProduk(c.getString(c.getColumnIndex(PRODUK)));
                    model.setType(c.getString(c.getColumnIndex(TYPE)));
                    model.setNilai(c.getInt(c.getColumnIndex(NILAI)));
                    model.setSatuan_nilai(c.getString(c.getColumnIndex(SATUAN_NILAI)));
                    model.setUsia_batas_bawah(c.getInt(c.getColumnIndex(USIA_BATAS_BAWAH)));
                    model.setUsia_batas_atas(c.getInt(c.getColumnIndex(USIA_BATAS_ATAS)));
                    model.setKeterangan(c.getString(c.getColumnIndex(KETERANGAN)));
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
