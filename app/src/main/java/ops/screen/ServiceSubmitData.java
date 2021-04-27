package ops.screen;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

//import id.co.smmf.data.androidjob.ListData;
import id.co.smmf.mobile.BuildConfig;


public class ServiceSubmitData extends IntentService {

    public Long id , idApps , idPerusahaan;
    public String ktp , phone , email, server,stringperusahaan, stringapps, nama,validnumber,group;

    public ServiceSubmitData(){
        super("ServiceSubmitData");
    }


    @Override

    protected void onHandleIntent(@Nullable Intent intent) {
//        id = intent.getLongExtra("id", 0);
        idApps = intent.getLongExtra("kode_app", 1L);
        idPerusahaan = intent.getLongExtra("kode_perusahaan", 1L);
        nama = intent.getStringExtra("nama");
        ktp = intent.getStringExtra("ktp");
        phone = intent.getStringExtra("no_hp");
        email = intent.getStringExtra("email");
        validnumber = intent.getStringExtra("valid");
        group = intent.getStringExtra("group");
        //SIT
        server = "https://sit.simasfinance.co.id/data/";

        //Prod
//        server = "https://get.smmf.co.id/data/";

        if (validnumber.equalsIgnoreCase("valid")){
            validnumber = phone;
        }
//        Log.i("TAG","Sukses Jalan Intent Service" + idApps + " idApps "  +  idPerusahaan + " " +nama + " "+ ktp + " " + phone + email+ validnumber + group);
//        ListData.SubmitSimasku(getApplicationContext(), idPerusahaan, idApps,id,ktp,phone,email);
//        ListData.RegisterSend(getApplicationContext(),0L,0L,0L,"SMMF","SIMASKU",ktp,phone,email,nama);
       /* ListData.setServer(server);
        if(BuildConfig.FLAVOR.equalsIgnoreCase("kusyariah"))
            ListData.SendRegister(getApplicationContext(),validnumber, "","SMMF","SIMASKU_SYARIAH",group,ktp,phone,email,nama);
        else{
            ListData.SendRegister(getApplicationContext(),validnumber, "","SMMF","SIMASKU",group,ktp,phone,email,nama);
//            Log.i("TAG","Sukses Jalan Intent Service" + idApps + " idApps "  +  idPerusahaan + " " +nama + " "+ ktp + " " + phone + email+ validnumber + group);
        }*/



    }

}
