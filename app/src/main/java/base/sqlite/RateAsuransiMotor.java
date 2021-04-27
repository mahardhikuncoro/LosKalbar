package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateAsuransiMotor {
    public static final String TABLE                ="RATE_ASURANSI_MOTOR";
    public static final String _ID                  ="_ID";
    public static final String BACKEND_ID           ="BACKEND_ID";
    public static final String PRODUK               ="PRODUK";
    public static final String TIPE                 ="TIPE";
    public static final String TENOR                ="TENOR";
    public static final String VALUE_ASURANSI       ="VALUE_ASURANSI";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +BACKEND_ID+" INTEGER, "
                    +TIPE+" TEXT, "
                    +PRODUK+" TEXT, "
                    +TENOR+" REAL, "
                    +VALUE_ASURANSI+" REAL); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public RateAsuransiMotor(Context context) {
        this.context = context;
    }

    private RateAsuransiMotor open() throws SQLException {
        SQLiteConfigKu config = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, config.getDatabaseName(), config.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public RateAsuransiMotorModel save(RateAsuransiMotorModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(BACKEND_ID, model.getBackendId());
        v.put(PRODUK, model.getProduk());
        v.put(TIPE, model.getTipe());
        v.put(TENOR, model.getTenor());
        v.put(VALUE_ASURANSI, model.getValue_asuransi());

        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public RateAsuransiMotorModel save(Integer backendId, String produk, String tipe, Integer tenor,
                                       Double valueAsuransi) {
        RateAsuransiMotorModel model = new RateAsuransiMotorModel();
        model.setBackendId(backendId);
        model.setProduk(produk);
        model.setTipe(tipe);
        model.setTenor(tenor);
        model.setValue_asuransi(valueAsuransi);
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

    public RateAsuransiMotorModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiMotorModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiMotorModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getInt(c.getColumnIndex(BACKEND_ID)));
                    model.setProduk(c.getString(c.getColumnIndex(PRODUK)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setTenor(c.getInt(c.getColumnIndex(TENOR)));
                    model.setValue_asuransi(c.getDouble(c.getColumnIndex(VALUE_ASURANSI)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public RateAsuransiMotorModel selectBy(String produk, String tipe, Integer tenor) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if ((produk != null)&&(tipe!=null))
            q += " AND "+PRODUK+" = '"+produk+"' AND "+TIPE+" = '"+tipe+"' AND "+TENOR+"="+tenor;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiMotorModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiMotorModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getInt(c.getColumnIndex(BACKEND_ID)));
                    model.setProduk(c.getString(c.getColumnIndex(PRODUK)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setTenor(c.getInt(c.getColumnIndex(TENOR)));
                    model.setValue_asuransi(c.getDouble(c.getColumnIndex(VALUE_ASURANSI)));
                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<RateAsuransiMotorModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<RateAsuransiMotorModel> list = new ArrayList<>();

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        RateAsuransiMotorModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new RateAsuransiMotorModel();
                    model.setId(c.getInt(c.getColumnIndex(_ID)));
                    model.setBackendId(c.getInt(c.getColumnIndex(BACKEND_ID)));
                    model.setProduk(c.getString(c.getColumnIndex(PRODUK)));
                    model.setTipe(c.getString((c.getColumnIndex(TIPE))));
                    model.setTenor(c.getInt(c.getColumnIndex(TENOR)));
                    model.setValue_asuransi(c.getDouble(c.getColumnIndex(VALUE_ASURANSI)));
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
