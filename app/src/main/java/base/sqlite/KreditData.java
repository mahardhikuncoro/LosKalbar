package base.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ops.screen.KreditModel;

/**
 * Created by ADMINSMMF on 11/24/2017.
 */

public class KreditData {
    public static final String TABLE     ="KREDIT_DATA";
    public static final String _ID       ="_ID";
    public static final String OTR = "OTR";
    public static final String DP = "DP";
    public static final String RATE_ASURANSI = "RATE_ASURANSI";
    public static final String BIAYA_ASURANSI = "BIAYA_ASURANSI";
    public static final String POKOK_HUTANG = "POKOK_HUTANG";
    public static final String POKOK_HUTANG_MOTOR = "POKOK_HUTANG_MOTOR";
    public static final String RATE_BUNGA = "RATE_BUNGA";
    public static final String NOMINAL_BUNGA = "NOMINAL_BUNGA";
    public static final String BIAYA_PROVISI = "BIAYA_PROVISI";
    public static final String ANGSURAN      ="ANGSURAN";
    public static final String TDP   ="TDP";
    public static final String TENOR = "TENOR";
    public static final String TAHUN_KENDARAAN = "TAHUN_KENDARAAN";
    public static final String USIA_KENDARAAN = "USIA_KENDARAAN";
    public static final String PRODUK = "PRODUK";
    public static final String BIAYA_ADMIN = "BIAYA_ADMIN";
    public static final String BIAYA_SURVEY = "BIAYA_SURVEY";
    public static final String BIAYA_FIDUCIA = "BIAYA_FIDUCIA";
    public static final String ASURANSI_JIWA = "ASURANSI_JIWA";
    public static final String INDICATOR_TAHUN = "INDICATOR_TAHUN";
    public static final String PENCAIRAN = "PENCAIRAN";

    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +OTR+" REAL, "
                    +DP+" REAL, "
                    +RATE_ASURANSI+" REAL, "
                    +POKOK_HUTANG+" TEXT, "
                    +POKOK_HUTANG_MOTOR+" TEXT, "
                    +RATE_BUNGA+" REAL, "
                    +NOMINAL_BUNGA+" REAL, "
                    +BIAYA_ASURANSI+" REAL, "
                    +BIAYA_PROVISI+" REAL, "
                    +ANGSURAN+" REAL, "
                    +TDP+" REAL, "
                    +TENOR+" INTEGER, "
                    +TAHUN_KENDARAAN+" INTEGER, "
                    +USIA_KENDARAAN+" INTEGER, "
                    +PRODUK+" TEXT, "
                    +INDICATOR_TAHUN+" TEXT, "
                    +BIAYA_ADMIN+" REAL, "
                    +BIAYA_SURVEY+" REAL, "
                    +BIAYA_FIDUCIA+" REAL, "
                    +PENCAIRAN+" REAL, "
                    +ASURANSI_JIWA+" REAL); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    public static final String ALTER_TABLE =
            " ALTER TABLE "+TABLE+" ADD ASURANSI_JIWA REAL";

    private final Context context;
    private SQLiteHelperBexi sqLiteHelperBexi;
    private SQLiteDatabase sqLiteDatabase;

    public KreditData(Context context) {
        this.context = context;
    }

    private KreditData open() throws SQLException {
        SQLiteConfigKu sqLiteConfigKu = new SQLiteConfigKu(context);
        sqLiteHelperBexi = new SQLiteHelperBexi(context, sqLiteConfigKu.getDatabaseName(), sqLiteConfigKu.getDatabaseVersion());
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public KreditModel save(KreditModel model) {
        open();
        sqLiteDatabase = sqLiteHelperBexi.getWritableDatabase();

        ContentValues v = new ContentValues();
//        v.put(OTR, model.getDana());
//        v.put(DP, model.getUangMuka());
        v.put(RATE_ASURANSI, model.getRateAsuransi());
        v.put(BIAYA_ASURANSI, model.getNominalAsuransiMobil());
//        v.put(BIAYA_PROVISI, model.getBiayaProvisi());
        v.put(ANGSURAN, model.getAngsuran());
        v.put(TDP, model.getTdp());
        v.put(POKOK_HUTANG, model.getPokokHutang());
        v.put(POKOK_HUTANG_MOTOR, model.getPokokHutangMotor());
        v.put(TENOR, model.getTenor());
        v.put(RATE_BUNGA,model.getRateBunga());
        v.put(NOMINAL_BUNGA, model.getNominalBunga());
        v.put(PENCAIRAN, model.getNominalPencairan());
//        v.put(USIA_KENDARAAN, model.getTahunKendaraan());
//        v.put(BIAYA_ADMIN, model.getBiayaAdmin());
//        v.put(BIAYA_SURVEY, model.getBiayaSurvey());
//        v.put(BIAYA_FIDUCIA, model.getBiayaFiducia());
//        v.put(ASURANSI_JIWA,model.getAsuransiJiwa());
        v.put(INDICATOR_TAHUN, model.getIndicatorTahun());

        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
//            Log.e("int ", String.valueOf(id.intValue()));
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

    public KreditModel save( Double pokokHutang, Double pokokHutangMotor, Integer tenor, Double rateBunga, Double nominalBunga,
                             Double rateAsuransi, Double angsuran, Double pencairan, Double tdp) {

//        Log.e("tenor", tenor.toString());
        KreditModel model = new KreditModel();
        model.setPokokHutang(pokokHutang);
        model.setPokokHutangMotor(pokokHutangMotor);
        model.setTenor(tenor);
        model.setRateBunga(rateBunga);
        model.setNominalBunga(nominalBunga);
        model.setRateAsuransi(rateAsuransi);
//        model.setNominalAsuransi(nominalAsuransi);
        model.setAngsuran(angsuran);
        model.setNominalPencairan(pencairan);
        model.setTdp(tdp);
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


    public KreditModel select(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        KreditModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new KreditModel();
                    model.setDana(c.getDouble(c.getColumnIndex(OTR)));
                    model.setUangMuka(c.getDouble(c.getColumnIndex(DP)));
                    model.setRateAsuransi(c.getDouble((c.getColumnIndex(RATE_ASURANSI))));
                    model.setPokokHutang(c.getDouble((c.getColumnIndex(POKOK_HUTANG))));
                    model.setPokokHutangMotor(c.getDouble(c.getColumnIndex(POKOK_HUTANG_MOTOR)));
                    model.setRateBunga(c.getDouble((c.getColumnIndex(RATE_BUNGA))));
                    model.setNominalBunga(c.getDouble((c.getColumnIndex(NOMINAL_BUNGA))));
                    model.setNominalAsuransiMobil(c.getDouble((c.getColumnIndex(BIAYA_ASURANSI))));
                    model.setBiayaProvisi(c.getDouble((c.getColumnIndex(BIAYA_PROVISI))));
                    model.setAngsuran(c.getDouble((c.getColumnIndex(ANGSURAN))));
                    model.setTdp(c.getDouble((c.getColumnIndex(TDP))));
                    model.setTenor(c.getInt((c.getColumnIndex(TENOR))));
                    model.setTahunKendaraan(c.getInt((c.getColumnIndex(TAHUN_KENDARAAN))));
                    model.setBiayaAdmin(c.getDouble((c.getColumnIndex(BIAYA_ADMIN))));
                    model.setBiayaSurvey(c.getDouble((c.getColumnIndex(BIAYA_SURVEY))));
                    model.setBiayaFiducia(c.getDouble((c.getColumnIndex(BIAYA_FIDUCIA))));
                    model.setAsuransiJiwa(c.getDouble((c.getColumnIndex(BIAYA_ASURANSI))));
                    model.setIndicatorTahun(c.getString(c.getColumnIndex(INDICATOR_TAHUN)));
                    model.setNominalPencairan(c.getDouble(c.getColumnIndex(PENCAIRAN)));


                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public KreditModel selectBy(Integer id) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (id != null)
            q += " AND "+_ID+"="+id;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        KreditModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new KreditModel();
                    model.setDana(c.getDouble(c.getColumnIndex(OTR)));
                    model.setUangMuka(c.getDouble(c.getColumnIndex(DP)));
                    model.setRateAsuransi(c.getDouble((c.getColumnIndex(RATE_ASURANSI))));
                    model.setPokokHutang(c.getDouble((c.getColumnIndex(POKOK_HUTANG))));
                    model.setPokokHutangMotor(c.getDouble(c.getColumnIndex(POKOK_HUTANG_MOTOR)));
                    model.setRateBunga(c.getDouble((c.getColumnIndex(RATE_BUNGA))));
                    model.setNominalBunga(c.getDouble((c.getColumnIndex(NOMINAL_BUNGA))));
                    model.setNominalAsuransiMobil(c.getDouble((c.getColumnIndex(BIAYA_ASURANSI))));
                    model.setBiayaProvisi(c.getDouble((c.getColumnIndex(BIAYA_PROVISI))));
                    model.setAngsuran(c.getDouble((c.getColumnIndex(ANGSURAN))));
                    model.setTdp(c.getDouble((c.getColumnIndex(TDP))));
                    model.setTenor(c.getInt((c.getColumnIndex(TENOR))));
                    model.setTahunKendaraan(c.getInt((c.getColumnIndex(TAHUN_KENDARAAN))));
                    model.setBiayaAdmin(c.getDouble((c.getColumnIndex(BIAYA_ADMIN))));
                    model.setBiayaSurvey(c.getDouble((c.getColumnIndex(BIAYA_SURVEY))));
                    model.setBiayaFiducia(c.getDouble((c.getColumnIndex(BIAYA_FIDUCIA))));
                    model.setAsuransiJiwa(c.getDouble((c.getColumnIndex(BIAYA_ASURANSI))));
                    model.setIndicatorTahun(c.getString(c.getColumnIndex(INDICATOR_TAHUN)));
                    model.setNominalPencairan(c.getDouble(c.getColumnIndex(PENCAIRAN)));

                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public KreditModel selectByTenor(Integer tenor) {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1 ";
        if (tenor != null)
            q += " AND "+TENOR+"="+tenor;

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        KreditModel model = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new KreditModel();
                    model.setDana(c.getDouble(c.getColumnIndex(OTR)));
                    model.setUangMuka(c.getDouble(c.getColumnIndex(DP)));
                    model.setRateAsuransi(c.getDouble((c.getColumnIndex(RATE_ASURANSI))));
                    model.setPokokHutang(c.getDouble((c.getColumnIndex(POKOK_HUTANG))));
                    model.setPokokHutangMotor(c.getDouble(c.getColumnIndex(POKOK_HUTANG_MOTOR)));
                    model.setRateBunga(c.getDouble((c.getColumnIndex(RATE_BUNGA))));
                    model.setNominalBunga(c.getDouble((c.getColumnIndex(NOMINAL_BUNGA))));
                    model.setNominalAsuransiMobil(c.getDouble((c.getColumnIndex(BIAYA_ASURANSI))));
                    model.setBiayaProvisi(c.getDouble((c.getColumnIndex(BIAYA_PROVISI))));
                    model.setAngsuran(c.getDouble((c.getColumnIndex(ANGSURAN))));
                    model.setTdp(c.getDouble((c.getColumnIndex(TDP))));
                    model.setTenor(c.getInt((c.getColumnIndex(TENOR))));
                    model.setTahunKendaraan(c.getInt((c.getColumnIndex(TAHUN_KENDARAAN))));
                    model.setIndicatorTahun(c.getString(c.getColumnIndex(INDICATOR_TAHUN)));
                    model.setBiayaAdmin(c.getDouble((c.getColumnIndex(BIAYA_ADMIN))));
                    model.setBiayaSurvey(c.getDouble((c.getColumnIndex(BIAYA_SURVEY))));
                    model.setBiayaFiducia(c.getDouble((c.getColumnIndex(BIAYA_FIDUCIA))));
                    model.setAsuransiJiwa(c.getDouble((c.getColumnIndex(BIAYA_ASURANSI))));
                    model.setNominalPencairan(c.getDouble(c.getColumnIndex(PENCAIRAN)));

                } while (c.moveToNext());
            }
        }
        close();
        return model;
    }

    public List<KreditModel> selectList() {
        String q = "SELECT * FROM "+TABLE+" ";
        List<KreditModel> list = new ArrayList<>();

//        Log.e("open sql", "tenor");

        open();
        sqLiteDatabase = sqLiteHelperBexi.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

//        Log.e("ke sini", "tenor");

        KreditModel model;
        if (c.moveToFirst()) {
            do {
//                Log.e("sini", "do");
                model = new KreditModel();
                model.setDana(c.getDouble(c.getColumnIndex(OTR)));
                model.setUangMuka(c.getDouble(c.getColumnIndex(DP)));
                model.setRateAsuransi(c.getDouble((c.getColumnIndex(RATE_ASURANSI))));
                model.setNominalAsuransiMobil(c.getDouble((c.getColumnIndex(BIAYA_ASURANSI))));
                model.setBiayaProvisi(c.getDouble((c.getColumnIndex(BIAYA_PROVISI))));
                model.setAngsuran(c.getDouble((c.getColumnIndex(ANGSURAN))));
                model.setTdp(c.getDouble((c.getColumnIndex(TDP))));
//                Log.e("cek", String.valueOf(c.getInt((c.getColumnIndex(TENOR)))));
                model.setPokokHutang(c.getDouble((c.getColumnIndex(POKOK_HUTANG))));
                model.setPokokHutangMotor(c.getDouble(c.getColumnIndex(POKOK_HUTANG_MOTOR)));
                model.setTenor(c.getInt((c.getColumnIndex(TENOR))));
                model.setRateBunga(c.getDouble((c.getColumnIndex(RATE_BUNGA))));
                model.setNominalBunga(c.getDouble((c.getColumnIndex(NOMINAL_BUNGA))));
                model.setIndicatorTahun(c.getString(c.getColumnIndex(INDICATOR_TAHUN)));
                model.setTahunKendaraan(c.getInt((c.getColumnIndex(TAHUN_KENDARAAN))));
                model.setBiayaAdmin(c.getDouble((c.getColumnIndex(BIAYA_ADMIN))));
                model.setBiayaSurvey(c.getDouble((c.getColumnIndex(BIAYA_SURVEY))));
                model.setBiayaFiducia(c.getDouble((c.getColumnIndex(BIAYA_FIDUCIA))));
                model.setAsuransiJiwa(c.getDouble((c.getColumnIndex(BIAYA_ASURANSI))));
                model.setNominalPencairan(c.getDouble(c.getColumnIndex(PENCAIRAN)));

                list.add(model);
            } while (c.moveToNext());
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
