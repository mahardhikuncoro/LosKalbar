package base.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelperBexi extends SQLiteOpenHelper {

    public SQLiteHelperBexi(Context context, String name, Integer ver) {
        super(context, name, null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("SUDAH ","CREATE TABLE");
        db.execSQL(SliderSQL.CREATE_TABLE);
        db.execSQL(Userdata.CREATE_TABLE);
        db.execSQL(FormData.CREATE_TABLE);
        db.execSQL(PartnerApplyImageData.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        onCreate(db);
        db.execSQL(SliderSQL.DROP_TABLE);
        db.execSQL(Userdata.DROP_TABLE);
        db.execSQL(SliderSQL.CREATE_TABLE);
        db.execSQL(Userdata.CREATE_TABLE);
        db.execSQL(PartnerApplyImageData.CREATE_TABLE);

    }
}