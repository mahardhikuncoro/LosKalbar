package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateAsuransiJiwa {
    public static final String TABLE                ="RATE_ASURANSI_JIWA";
    public static final String _ID                  ="_ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String KETERANGAN           ="KETERANGAN";
    public static final String BATAS_ATAS           ="BATAS_ATAS";
    public static final String BATAS_BAWAH          ="BATAS_BAWAH";
    public static final String BIAYA_TAHUN1         ="BIAYA_TAHUN1";
    public static final String BIAYA_TAHUN2         ="BIAYA_TAHUN2";
    public static final String BIAYA_TAHUN3         ="BIAYA_TAHUN3";
    public static final String BIAYA_TAHUN4         ="BIAYA_TAHUN4";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +KETERANGAN+" TEXT, "
                    +BATAS_ATAS+" REAL, "
                    +BATAS_BAWAH+" REAL, "
                    +BIAYA_TAHUN1+" REAL, "
                    +BIAYA_TAHUN2+" REAL, "
                    +BIAYA_TAHUN3+" REAL, "
                    +BIAYA_TAHUN4+" REAL); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public RateAsuransiJiwa(Context context) {
        this.context = context;
    }

    private RateAsuransiJiwa open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public RateAsuransiJiwaModel save(RateAsuransiJiwaModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getId());
        v.put(KETERANGAN, model.getKeterangan());
        v.put(BATAS_ATAS, model.getBatas_atas());
        v.put(BATAS_BAWAH, model.getBatas_bawah());
        v.put(BIAYA_TAHUN1, model.getBiaya_tahun_1());
        v.put(BIAYA_TAHUN2, model.getBiaya_tahun_2());
        v.put(BIAYA_TAHUN3, model.getBiaya_tahun_3());
        v.put(BIAYA_TAHUN4, model.getBiaya_tahun_4());



        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public RateAsuransiJiwaModel save(Integer backendId, String keterangan, Double batasAtas, Double batasBawah, Double biayaTahun1,
                                      Double biayaTahun2, Double biayaTahun3, Double biayaTahun4) {
        RateAsuransiJiwaModel model = new RateAsuransiJiwaModel();
        model.setBackendId(backendId);
        model.setKeterangan(keterangan);
        model.setBatas_atas(batasAtas);
        model.setBatas_bawah(batasBawah);
        model.setBiaya_tahun_1(biayaTahun1);
        model.setBiaya_tahun_2(biayaTahun2);
        model.setBiaya_tahun_3(biayaTahun3);
        model.setBiaya_tahun_4(biayaTahun4);

//        Log.e("BA ASJ", model.getBatasAtas().toString());
//        Log.e("BW ASJ", model.getBatasBawah().toString());

//        Log.e("ASJ 1", model.getBiaya_tahun_1().toString());
//        Log.e("ASJ 2", model.getBiaya_tahun_1().toString());
//        Log.e("ASJ 3", model.getBiaya_tahun_1().toString());
//        Log.e("ASJ 4", model.getBiaya_tahun_1().toString());
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

    public RateAsuransiJiwaModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiJiwaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiJiwaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setKeterangan(c.getString(c.getColumnIndex(KETERANGAN)));
                    model.setBatas_atas(c.getDouble(c.getColumnIndex(BATAS_ATAS)));
                    model.setBatas_bawah(c.getDouble(c.getColumnIndex(BATAS_BAWAH)));
                    model.setBiaya_tahun_1(c.getDouble(c.getColumnIndex(BIAYA_TAHUN1)));
                    model.setBiaya_tahun_2(c.getDouble(c.getColumnIndex(BIAYA_TAHUN2)));
                    model.setBiaya_tahun_3(c.getDouble(c.getColumnIndex(BIAYA_TAHUN3)));
                    model.setBiaya_tahun_4(c.getDouble(c.getColumnIndex(BIAYA_TAHUN4)));

                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public RateAsuransiJiwaModel selectBetween(Double value) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (value != null)
            q += " AND "+BATAS_ATAS+">="+value+" AND "+BATAS_BAWAH+"<="+value;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiJiwaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiJiwaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setKeterangan(c.getString(c.getColumnIndex(KETERANGAN)));
                    model.setBatas_atas(c.getDouble(c.getColumnIndex(BATAS_ATAS)));
                    model.setBatas_bawah(c.getDouble(c.getColumnIndex(BATAS_BAWAH)));
                    model.setBiaya_tahun_1(c.getDouble(c.getColumnIndex(BIAYA_TAHUN1)));
                    model.setBiaya_tahun_2(c.getDouble(c.getColumnIndex(BIAYA_TAHUN2)));
                    model.setBiaya_tahun_3(c.getDouble(c.getColumnIndex(BIAYA_TAHUN3)));
                    model.setBiaya_tahun_4(c.getDouble(c.getColumnIndex(BIAYA_TAHUN4)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public RateAsuransiJiwaModel selectBy(Double batasAtas, Double batasBawah) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((batasAtas != null)&&(batasBawah != null))
            q += " AND "+BATAS_ATAS+"="+batasAtas+" AND "+BATAS_BAWAH+"="+batasBawah;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiJiwaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiJiwaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setKeterangan(c.getString(c.getColumnIndex(KETERANGAN)));
                    model.setBatas_atas(c.getDouble(c.getColumnIndex(BATAS_ATAS)));
                    model.setBatas_bawah(c.getDouble(c.getColumnIndex(BATAS_BAWAH)));
                    model.setBiaya_tahun_1(c.getDouble(c.getColumnIndex(BIAYA_TAHUN1)));
                    model.setBiaya_tahun_2(c.getDouble(c.getColumnIndex(BIAYA_TAHUN2)));
                    model.setBiaya_tahun_3(c.getDouble(c.getColumnIndex(BIAYA_TAHUN3)));
                    model.setBiaya_tahun_4(c.getDouble(c.getColumnIndex(BIAYA_TAHUN4)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<RateAsuransiJiwaModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<RateAsuransiJiwaModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiJiwaModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiJiwaModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setKeterangan(c.getString(c.getColumnIndex(KETERANGAN)));
                    model.setBatas_atas(c.getDouble(c.getColumnIndex(BATAS_ATAS)));
                    model.setBatas_bawah(c.getDouble(c.getColumnIndex(BATAS_BAWAH)));
                    model.setBiaya_tahun_1(c.getDouble(c.getColumnIndex(BIAYA_TAHUN1)));
                    model.setBiaya_tahun_2(c.getDouble(c.getColumnIndex(BIAYA_TAHUN2)));
                    model.setBiaya_tahun_3(c.getDouble(c.getColumnIndex(BIAYA_TAHUN3)));
                    model.setBiaya_tahun_4(c.getDouble(c.getColumnIndex(BIAYA_TAHUN4)));
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
