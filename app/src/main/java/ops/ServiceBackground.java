package ops;


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
//import id.co.smma.data.androidjob.ListData;

public class ServiceBackground extends IntentService {

    public ServiceBackground(){
        super("ServiceBackground");
    }

    @Override

    protected void onHandleIntent(@Nullable Intent intent) {
//        Log.i("TAG","Sukses Jalan Intent Service");
//        ListData.Register(getApplicationContext());

    }

}
