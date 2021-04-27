package base.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.PushNotificationJson;
import base.sqlite.SQLiteConfigKu;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserTypeService extends IntentService {

    private ResultReceiver resultReceiverLender;
    private SQLiteConfigKu config;

    private String pushMessagingId;
    private String criteria1;
    private String criteria2;
    private String criteria3;
    private EndPoint endPoint;


    public UserTypeService() {
        super(UserTypeService.class.getName());
    }

    @Override
    public void onHandleIntent(Intent intent) {
        resultReceiverLender = intent.getParcelableExtra("_HomeServiceLender");
        criteria1 = intent.getExtras().getString("appName");
        criteria2 = intent.getExtras().getString("username");
        criteria3 = intent.getExtras().getString("imei");
        initiateApiData();
        getToken();

    }

    private void getToken() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    FirebaseMessaging.getInstance().setAutoInitEnabled(true);
                    FirebaseMessaging.getInstance().subscribeToTopic("all");
                    FirebaseInstanceId newToken = FirebaseInstanceId.getInstance(FirebaseApp.initializeApp(UserTypeService.this));
                    newToken.getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (task.isSuccessful()) {
                                pushMessagingId = task.getResult().getToken();
                                Log.e("PUSH MESSAGE ID " ," : " + pushMessagingId);
                                registerTokenNotofification(pushMessagingId);
                            } else
                                getToken();

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void registerTokenNotofification(String token){
        final PushNotificationJson.registerTokenIdRequest request = new PushNotificationJson().new registerTokenIdRequest();
        final PushNotificationJson.registerTokenIdRequest.PushDatamodel requestModel = new PushNotificationJson().new registerTokenIdRequest().new PushDatamodel();

        requestModel.setCriteria1(criteria1);
        requestModel.setCriteria2(criteria2);
        requestModel.setCriteria3(criteria3);
        requestModel.setCriteria4("");
        requestModel.setCriteria5("");
        requestModel.setInstanceIdToken(token);
        requestModel.setCreatedBy(criteria2);
        request.setModel(requestModel);

        Call<PushNotificationJson.registerTokenIdCallback> call = endPoint.registerTokenId(request);
        call.enqueue(new Callback<PushNotificationJson.registerTokenIdCallback>() {
            @Override
            public void onResponse(Call<PushNotificationJson.registerTokenIdCallback> call, Response<PushNotificationJson.registerTokenIdCallback> response) {
                if(response.isSuccessful())
                    Log.e("Sukses Register", "Token ");
                else
                    Log.e("Failed Register", "Token ");
            }

            @Override
            public void onFailure(Call<PushNotificationJson.registerTokenIdCallback> call, Throwable t) {
                Log.e("Failed Register", "Token ");
            }
        });
    }
    private void initiateApiData(){
        config = new SQLiteConfigKu(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint = retrofit.create(EndPoint.class);
    }

//    private void callActivateDevice(final String email) {
//        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String imei = telephonyManager.getDeviceId();
//        if (imei == null) imei = "";
//        String manufacturer = Build.MANUFACTURER;
//        if (manufacturer == null) manufacturer = "";
//        String brand = Build.BRAND;
//        if (brand == null) brand = "";
//        String product = Build.PRODUCT;
//        if (product == null) product = "";
//        String model = Build.MODEL;
//        if (model == null) model = "";
//        String os = Build.VERSION.SDK_INT + "";
//
//        /*try {
//         *//*InstanceID instanceIDnew = InstanceID.getInstance(this);
//            pushMessagingId = instanceIDnew.getToken(config.getGoogleProjectNumber(), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);*//*
//        } catch (IOException e) {
//            e.printStackTrace();
//        }*/
//
//        final ActivateJson.Request request = new ActivateJson(). new Request();
//        request.setV(config.getServerBVersion());
//        request.setUn(email);
//        request.setIm(imei+"_");
//        request.setMf(manufacturer);
//        request.setB(brand);
//        request.setP(product);
//        request.setM(model);
//        request.setOs(os);
//        request.setPmi(pushMessagingId);
//
//        Call<ActivateJson.Callback> call = b.activated(request);
//        call.enqueue(new Callback<ActivateJson.Callback>() {
//            @Override
//            public void onResponse(Call<ActivateJson.Callback> call, Response<ActivateJson.Callback> response) {
//                if (response.isSuccessful()) {
//                    if (ResponseStatus.OK.name().equalsIgnoreCase(response.body().getRs())) {
//                        resultActivateDevice(response.body().getUdi());
//                        Log.e("udi", response.body().getUdi()+"");
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ActivateJson.Callback> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//
//    private void resultActivateDevice(Long deviceId) {
//        activeUserMetaData.setDeviceId(deviceId);
//        userData.save(activeUserMetaData);
//
//        Bundle bundle = new Bundle();
//        if (activeUserMetaData.getUserType().equalsIgnoreCase("L"))
//            resultReceiverLender.send(0, bundle);
//        else
//            resultReceiverBorrower.send(0, bundle);
//    }
}
