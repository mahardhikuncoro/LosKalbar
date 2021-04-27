package ops;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.ParameterJson;
import base.network.Slider;
import base.network.SliderJson;
import base.sqlite.Userdata;
import base.sqlite.IndicatorHarga;
import base.sqlite.IndicatorHargaModel;
import base.sqlite.IndicatorTahun;
import base.sqlite.IndicatorTahunModel;
import base.sqlite.ParamKategoriAsuransi;
import base.sqlite.ParamKategoriAsuransiModel;
import base.sqlite.ParamPenyusutan;
import base.sqlite.ParamPenyusutanModel;
import base.sqlite.ParamPinjamanExtended;
import base.sqlite.ParamPinjamanExtendedModel;
import base.sqlite.ParameterPinjaman;
import base.sqlite.ParameterPinjamanModel;
import base.sqlite.ParameterTenor;
import base.sqlite.ParameterTenorModel;
import base.sqlite.RateAsuransi;
import base.sqlite.RateAsuransiJiwa;
import base.sqlite.RateAsuransiJiwaModel;
import base.sqlite.RateAsuransiModel;
import base.sqlite.RateAsuransiMotor;
import base.sqlite.RateAsuransiMotorModel;
import base.sqlite.RateBiaya;
import base.sqlite.RateBiayaModel;
import base.sqlite.RateBunga;
import base.sqlite.RateBungaModel;
import base.sqlite.SQLiteConfig;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.SliderSQL;
import butterknife.BindString;
import id.co.smmf.mobile.R;
import ops.screen.MainActivityDashboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeActivity extends AppCompatActivity {

    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;
    private EndPoint endPoint;

    private ParameterPinjaman parameterPinjaman;
    private ParameterPinjamanModel parameterPinjamanModel;

    private IndicatorHarga indicatorHarga;
    private IndicatorHargaModel indicatorHargaModel;

    private IndicatorTahun indicatorTahun;
    private IndicatorTahunModel indicatorTahunModel;

    private RateBunga rateBunga;
    private RateBungaModel rateBungaModel;

    private RateAsuransi rateAsuransi;
    private RateAsuransiModel rateAsuransiModel;

    private RateAsuransiMotor rateAsuransiMotor;
    private RateAsuransiMotorModel rateAsuransiMotorModel;

    private RateAsuransiJiwa rateAsuransiJiwa;
    private RateAsuransiJiwaModel rateAsuransiJiwaModel;

    private RateBiaya rateBiaya;
    private RateBiayaModel rateBiayaModel;

    private ParamPenyusutan paramPenyusutan;
    private ParamPenyusutanModel paramPenyusutanModel;

    private ParamKategoriAsuransi paramKategoriAsuransi;
    private ParamKategoriAsuransiModel paramKategoriAsuransiModel;

    private ParamPinjamanExtended paramPinjamanExtended;
    private ParamPinjamanExtendedModel paramPinjamanExtendedModel;

    private ParameterTenor parameterTenor;
    private ParameterTenorModel parameterTenorModel;

    private ArrayList<String> parPinjamanList;
    private ArrayList<ParameterPinjamanModel> parPinjamanDataList;

    private ArrayList<String> paramPinjamanExtendedList;
    private ArrayList<ParamPinjamanExtendedModel> paramPinjamanExtendedDataList;

    private ArrayList<String> indicatorHargaList;
    private ArrayList<IndicatorHargaModel> indicatorHargaDataList;

    private ArrayList<String> indicatorTahunList;
    private ArrayList<IndicatorTahunModel> indicatorTahunDataList;

    private ArrayList<String> rateBungaList;
    private ArrayList<RateBungaModel> rateBungaDatalist;

    private ArrayList<String> rateAsuransiList;
    private ArrayList<RateAsuransiModel> rateAsuransiDataList;

    private ArrayList<String> rateAsuransiMotorList;
    private ArrayList<RateAsuransiMotorModel> rateAsuransiMotorDataList;

    private ArrayList<String> rateAsuransiJiwaList;
    private ArrayList<RateAsuransiJiwaModel> rateAsuransiJiwaDataList;

    private ArrayList<String> rateBiayaList;
    private ArrayList<RateBiayaModel> rateBiayaDataList;

    private ArrayList<String> katAsuransiList;
    private ArrayList<ParamKategoriAsuransiModel> katAsuransiDataList;

    private ArrayList<String> paramPenyusutanList;
    private ArrayList<ParamPenyusutanModel> paramPenyusutanDataList;

    private ArrayList<String> paramTenorList;
    private ArrayList<ParameterTenorModel> paramTenorDataList;

    private ArrayList<String> sliderList;
    private ArrayList<Slider> sliderDataList;
    private SliderSQL sliderdql;

    SharedPreferences sharedpreferences;
    public static final String useridPref = "userid";
    public static final String simaskupref = "simaskupref";
    private String cust_id,noHp;
    private String UserImei = "";
    private Userdata datapribadi;



    private EndPoint endPoint2;
    private MaterialDialog dialog;
    private Bundle bundle;

    private Userdata userdata;

    @BindString(R.string.buildName)
    String buildName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_bexi);
        initialize();
        callVersion();
    }

    private void initialize() {

        datapribadi = new Userdata(this);
        if(datapribadi.count() != 0)
            noHp = datapribadi.select().getBranchname();
        else
            noHp = "";
        sharedpreferences = getSharedPreferences(simaskupref, Context.MODE_PRIVATE);


        sqLiteConfigKu = new SQLiteConfigKu(this);
        networkConnection = new NetworkConnection(this);
        parameterPinjamanModel = new ParameterPinjamanModel();
        parameterPinjaman = new ParameterPinjaman(this);
        indicatorHargaModel = new IndicatorHargaModel();
        indicatorHarga = new IndicatorHarga(this);
        indicatorTahunModel = new IndicatorTahunModel();
        indicatorTahun = new IndicatorTahun(this);
        rateBungaModel = new RateBungaModel();
        rateBunga = new RateBunga(this);
        rateAsuransiModel = new RateAsuransiModel();
        rateAsuransi = new RateAsuransi(this);
        rateAsuransiJiwaModel = new RateAsuransiJiwaModel();
        rateAsuransiJiwa = new RateAsuransiJiwa(this);
        rateAsuransiMotorModel = new RateAsuransiMotorModel();
        rateAsuransiMotor = new RateAsuransiMotor(this);
        rateBiayaModel = new RateBiayaModel();
        rateBiaya = new RateBiaya(this);
        paramKategoriAsuransiModel = new ParamKategoriAsuransiModel();
        paramKategoriAsuransi = new ParamKategoriAsuransi(this);
        paramPenyusutanModel = new ParamPenyusutanModel();
        paramPenyusutan = new ParamPenyusutan(this);
        parameterTenorModel = new ParameterTenorModel();
        parameterTenor = new ParameterTenor(this);
        paramPinjamanExtendedModel = new ParamPinjamanExtendedModel();
        paramPinjamanExtended = new ParamPinjamanExtended(this);
        sliderdql = new SliderSQL(this);
        bundle = new Bundle();

//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            dialogPermission();
//        }else{
//            UserImei = telephonyManager.getDeviceId();
//        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint = retrofit.create(EndPoint.class);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServerOtp())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint2 = retrofit2.create(EndPoint.class);
        userdata = new Userdata(getApplicationContext());
    }

    private void callVersion(){

        if (! networkConnection.isNetworkConnected()){
            dialog(R.string.errorNoInternetConnection);
        }else {
            dialog  = new MaterialDialog.Builder(this)
                    .content("Checking Version....")
                    .progress(true, 0)
                    .cancelable(false)
                    .show();

            final ParameterJson.VersionRequest request = new ParameterJson().new VersionRequest();
            request.setVersion(sqLiteConfigKu.getServerVersion());
            Log.e("VERSION "," SERVER = " + sqLiteConfigKu.getServerVersion());

            Call<ParameterJson.VersionCallback> call = endPoint.checkVersion(request);
            call.enqueue(new Callback<ParameterJson.VersionCallback>() {
                @Override
                public void onResponse(Call<ParameterJson.VersionCallback> call, Response<ParameterJson.VersionCallback> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            if (response.body().getResponseStatus().equalsIgnoreCase("COMPATIBLE_VERSION")){
                                callApiSlider();
                            }else if (response.body().getResponseStatus().equals("INCOMPATIBLE_VERSION")) {
                                dialog.dismiss();
                                dialogPlaystore(R.string.errorIncompatibleVersion);
                            }else{
                                dialog.dismiss();
                                dialog(R.string.errorConnection);
                            }
                        }else{
                            dialog.dismiss();
                            dialog(R.string.errorConnection);
                        }
                    }else{
                        dialog.dismiss();
                        callVersion();
                    }
                }

                @Override
                public void onFailure(Call<ParameterJson.VersionCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            });
        }

    }

    private void getParameterPinjaman() {

        if (!networkConnection.isNetworkConnected()) {
            dialog(R.string.errorNoInternetConnection);
        } else {


            parameterPinjaman.deleteAll();

            Call<ParameterJson.PinjamanCallback> call = endPoint.getParameterPinjaman();
            call.enqueue(new Callback<ParameterJson.PinjamanCallback>() {
                @Override
                public void onResponse(Call<ParameterJson.PinjamanCallback> call, Response<ParameterJson.PinjamanCallback> response) {
                    if (response.body().getRs().equals("OK")) {
                        parPinjamanDataList = new ArrayList<ParameterPinjamanModel>();
                        parPinjamanList = new ArrayList<String>();

                        for (ParameterJson.PinjamanCallback.ParamPinjaman model : response.body().getL()) {

                            ParameterPinjamanModel parameterPinjamanModel = new ParameterPinjamanModel();
                            parameterPinjamanModel.setBackendId(model.getId());
                            parameterPinjamanModel.setParameter(model.getParameter());
                            parameterPinjamanModel.setDescription(model.getDescription());
                            parameterPinjamanModel.setValue_mobil(model.getValue_mobil());
                            parameterPinjamanModel.setValue_motor(model.getValue_motor());

                            parPinjamanDataList.add(parameterPinjamanModel);
                            parPinjamanList.add(model.getParameter());

                            parameterPinjaman.save(model.getId(), model.getParameter(), model.getDescription(),
                                    model.getValue_mobil(), model.getValue_motor());

                        }
                        getParamPinjamanExtended();

                    } else {
                        dialog.dismiss();
                        dialog(R.string.errorConnection);
                    }
                }

                @Override
                public void onFailure(Call<ParameterJson.PinjamanCallback> call, Throwable t) {

                }
            });

        }
    }

    private void getParamPinjamanExtended() {

        paramPinjamanExtended.deleteAll();

        Call<ParameterJson.PinjamanExtendedCallback> call = endPoint.getPinjamanExtended();
        call.enqueue(new Callback<ParameterJson.PinjamanExtendedCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.PinjamanExtendedCallback> call, Response<ParameterJson.PinjamanExtendedCallback> response) {
                if (response.body().getRs().equals("OK")) {
                    paramPinjamanExtendedDataList = new ArrayList<ParamPinjamanExtendedModel>();
                    paramPinjamanExtendedList = new ArrayList<String>();

                    for (ParameterJson.PinjamanExtendedCallback.ParamPinjamanExtended model : response.body().getL()) {

                        ParamPinjamanExtendedModel paramPinjamanExtendedModel = new ParamPinjamanExtendedModel();
                        paramPinjamanExtendedModel.setBackendId(model.getId());
                        paramPinjamanExtendedModel.setProduk(model.getProduk());
                        paramPinjamanExtendedModel.setType(model.getType());
                        paramPinjamanExtendedModel.setNilai(model.getNilai());
                        paramPinjamanExtendedModel.setSatuan_nilai(model.getSatuan_nilai());
                        paramPinjamanExtendedModel.setUsia_batas_bawah(model.getUsia_batas_bawah());
                        paramPinjamanExtendedModel.setUsia_batas_atas(model.getUsia_batas_atas());
                        paramPinjamanExtendedModel.setKeterangan(model.getKeterangan());

                        paramPinjamanExtendedDataList.add(paramPinjamanExtendedModel);
                        paramPinjamanExtendedList.add(model.getKeterangan());

//                            Log.e("indicator harga",model.getIndicator_harga());

                        paramPinjamanExtended.save(model.getId(), model.getProduk(), model.getType(), model.getNilai(),
                                model.getSatuan_nilai(), model.getUsia_batas_bawah(), model.getUsia_batas_atas(),
                                model.getKeterangan());

//                        Log.e("Produk", paramPinjamanExtendedModel.getProduk());
//                        Log.e("Type", paramPinjamanExtendedModel.getType());
//                        Log.e("Nilai", paramPinjamanExtendedModel.getNilai().toString());
//                        Log.e("Usia Bawah", paramPinjamanExtendedModel.getUsia_batas_bawah().toString());
//                        Log.e("Usia Atas", paramPinjamanExtendedModel.getUsia_batas_atas().toString());

                    }
                    getParamTenor();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.PinjamanExtendedCallback> call, Throwable t) {

            }
        });


    }

    private void getIndicatorHarga() {

//        if (! networkConnection.isNetworkConnected()){
//            dialog(R.string.errorNoInternetConnection);
//        } else {
//            dialog = new MaterialDialog.Builder(this)
//                    .content(R.string.loading)
//                    .progress(true, 0)
//                    .cancelable(false)
//                    .show();
        indicatorHarga.deleteAll();

//            Log.e("call api Harga", "ini");

        Call<ParameterJson.IndicatorHargaCallback> call = endPoint.getIndicatorHarga();
        call.enqueue(new Callback<ParameterJson.IndicatorHargaCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.IndicatorHargaCallback> call, Response<ParameterJson.IndicatorHargaCallback> response) {
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();

//                        Log.e("RS Harga", "ok");
                    indicatorHargaDataList = new ArrayList<IndicatorHargaModel>();
                    indicatorHargaList = new ArrayList<String>();

                    for (ParameterJson.IndicatorHargaCallback.IndicatorHarga model : response.body().getL()) {

                        IndicatorHargaModel indicatorHargaModel = new IndicatorHargaModel();
                        indicatorHargaModel.setBackendId(model.getId());
                        indicatorHargaModel.setBatasAtas(model.getBatas_atas());
                        indicatorHargaModel.setBatasBawah(model.getBatas_bawah());
                        indicatorHargaModel.setIndicator_harga(model.getIndicator_harga());

                        indicatorHargaDataList.add(indicatorHargaModel);
                        indicatorHargaList.add(model.getIndicator_harga());

//                            Log.e("indicator harga",model.getIndicator_harga());

                        indicatorHarga.save(model.getId(), model.getBatas_atas(), model.getBatas_bawah(), model.getIndicator_harga());

//                            Log.e("indicator harga", indicatorHargaModel.getIndicator_harga());

                    }
                    getIndicatorTahun();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.IndicatorHargaCallback> call, Throwable t) {
                Log.e("error", t.getLocalizedMessage());
            }
        });

    }

    private void getIndicatorTahun() {

//        if (! networkConnection.isNetworkConnected()){
//            dialog(R.string.errorNoInternetConnection);
//        } else {
//            dialog = new MaterialDialog.Builder(this)
//                    .content(R.string.loading)
//                    .progress(true, 0)
//                    .cancelable(false)
//                    .show();

        indicatorTahun.deleteAll();

        Call<ParameterJson.IndicatorTahunCallback> call = endPoint.getIndicatorTahun();
        call.enqueue(new Callback<ParameterJson.IndicatorTahunCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.IndicatorTahunCallback> call, Response<ParameterJson.IndicatorTahunCallback> response) {
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();
                    indicatorTahunDataList = new ArrayList<IndicatorTahunModel>();
                    indicatorTahunList = new ArrayList<String>();

                    for (ParameterJson.IndicatorTahunCallback.IndicatorTahun model : response.body().getL()) {

                        IndicatorTahunModel indicatorTahunModel = new IndicatorTahunModel();
                        indicatorTahunModel.setBackendId(model.getId());
                        indicatorTahunModel.setBatasAtas(model.getBatas_atas());
                        indicatorTahunModel.setBatasBawah(model.getBatas_bawah());
                        indicatorTahunModel.setIndicator_tahun(model.getIndicator_tahun());

                        indicatorTahunDataList.add(indicatorTahunModel);
                        indicatorTahunList.add(model.getIndicator_tahun());

                        indicatorTahun.save(model.getId(), model.getBatas_atas(), model.getBatas_bawah(), model.getIndicator_tahun());


                    }
                    getRateBunga();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }

            }

            @Override
            public void onFailure(Call<ParameterJson.IndicatorTahunCallback> call, Throwable t) {

            }
        });

    }

    private void getRateBunga() {

//        if (! networkConnection.isNetworkConnected()){
//            dialog(R.string.errorNoInternetConnection);
//        } else {
//            dialog = new MaterialDialog.Builder(this)
//                    .content(R.string.loading)
//                    .progress(true, 0)
//                    .cancelable(false)
//                    .show();

        rateBunga.deleteAll();

        Call<ParameterJson.RateBungaCallback> call = endPoint.getRateBunga();
        call.enqueue(new Callback<ParameterJson.RateBungaCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.RateBungaCallback> call, Response<ParameterJson.RateBungaCallback> response) {
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();
                    rateBungaDatalist = new ArrayList<RateBungaModel>();
                    rateBungaList = new ArrayList<String>();

                    for (ParameterJson.RateBungaCallback.RateBunga model : response.body().getL()) {

                        RateBungaModel rateBungaModel = new RateBungaModel();
                        rateBungaModel.setBackendId(model.getId());
                        rateBungaModel.setTipe(model.getTipe());
                        rateBungaModel.setProduk(model.getProduk());
                        rateBungaModel.setIndicatorTahun(model.getIndicator_tahun());
                        rateBungaModel.setRateTahun1(model.getRate_tahun_1());
                        rateBungaModel.setRateTahun2(model.getRate_tahun_2());
                        rateBungaModel.setRateTahun3(model.getRate_tahun_3());
                        rateBungaModel.setRateTahun4(model.getRate_tahun_4());

                        rateBungaDatalist.add(rateBungaModel);
                        rateBungaList.add(model.getIndicator_tahun());

                        rateBunga.save(model.getId(), model.getTipe(), model.getProduk(), model.getIndicator_tahun(),
                                model.getRate_tahun_1(), model.getRate_tahun_2(), model.getRate_tahun_3(), model.getRate_tahun_4());

                    }
                    getRateAsuransi();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.RateBungaCallback> call, Throwable t) {

            }
        });

    }

    private void getRateAsuransi() {

//        if (! networkConnection.isNetworkConnected()){
//            dialog(R.string.errorNoInternetConnection);
//        } else {
//            dialog = new MaterialDialog.Builder(this)
//                    .content(R.string.loading)
//                    .progress(true, 0)
//                    .cancelable(false)
//                    .show();

        rateAsuransi.deleteAll();

        Call<ParameterJson.RateAsuransiCallback> call = endPoint.getRateAsuransiMobil();
        call.enqueue(new Callback<ParameterJson.RateAsuransiCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.RateAsuransiCallback> call, Response<ParameterJson.RateAsuransiCallback> response) {
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();
                    rateAsuransiDataList = new ArrayList<RateAsuransiModel>();
                    rateAsuransiList = new ArrayList<String>();

                    for (ParameterJson.RateAsuransiCallback.RateAsuransi model : response.body().getL()) {

                        RateAsuransiModel rateAsuransiModel = new RateAsuransiModel();
                        rateAsuransiModel.setBackendId(model.getId());
                        rateAsuransiModel.setTipe(model.getTipe());
                        rateAsuransiModel.setIndicatorHarga(model.getIndicator_harga());
                        rateAsuransiModel.setKategoriAsuransi(model.getKategori_asuransi());
                        rateAsuransiModel.setRateWilayah1(model.getRate_wilayah_1());
                        rateAsuransiModel.setRateWilayah2(model.getRate_wilayah_2());
                        rateAsuransiModel.setRateWilayah3(model.getRate_wilayah_3());

                        rateAsuransiDataList.add(rateAsuransiModel);
                        rateAsuransiList.add(model.getKategori_asuransi());

                        rateAsuransi.save(model.getId(), model.getTipe(), model.getIndicator_harga(),
                                model.getKategori_asuransi(), model.getRate_wilayah_1(), model.getRate_wilayah_2(),
                                model.getRate_wilayah_3());

                    }
                    getRateAsuransiMotor();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.RateAsuransiCallback> call, Throwable t) {

            }
        });

    }

    private void getRateAsuransiMotor() {

//        if (! networkConnection.isNetworkConnected()){
//            dialog(R.string.errorNoInternetConnection);
//        } else {
//            dialog = new MaterialDialog.Builder(this)
//                    .content(R.string.loading)
//                    .progress(true, 0)
//                    .cancelable(false)
//                    .show();

        rateAsuransiMotor.deleteAll();

        Call<ParameterJson.RateAsuransiMotorCallback> call = endPoint.getRateAsuransiMotor();
        call.enqueue(new Callback<ParameterJson.RateAsuransiMotorCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.RateAsuransiMotorCallback> call, Response<ParameterJson.RateAsuransiMotorCallback> response) {
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();
                    rateAsuransiMotorDataList = new ArrayList<RateAsuransiMotorModel>();
                    rateAsuransiMotorList = new ArrayList<String>();

                    for (ParameterJson.RateAsuransiMotorCallback.RateAsuransiMotor model : response.body().getL()) {

                        RateAsuransiMotorModel rateAsuransiMotorModel = new RateAsuransiMotorModel();
                        rateAsuransiMotorModel.setBackendId(model.getId());
                        rateAsuransiMotorModel.setProduk(model.getProduk());
                        rateAsuransiMotorModel.setTipe(model.getTipe());
                        rateAsuransiMotorModel.setTenor(model.getTenor());
                        rateAsuransiMotorModel.setValue_asuransi(model.getValue_asuransi());

                        rateAsuransiMotorDataList.add(rateAsuransiMotorModel);
                        rateAsuransiMotorList.add(model.getProduk());

                        rateAsuransiMotor.save(model.getId(), model.getProduk(), model.getTipe(), model.getTenor(), model.getValue_asuransi());

                        rateAsuransiMotorModel.getProduk();

                    }
                    getAsuransiJiwa();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.RateAsuransiMotorCallback> call, Throwable t) {

            }
        });

    }

    private void getAsuransiJiwa() {

//        if (! networkConnection.isNetworkConnected()){
//            dialog(R.string.errorNoInternetConnection);
//        } else {
//            dialog = new MaterialDialog.Builder(this)
//                    .content(R.string.loading)
//                    .progress(true, 0)
//                    .cancelable(false)
//                    .show();

        rateAsuransiJiwa.deleteAll();

        Call<ParameterJson.RateAsuransiJiwaCallback> call = endPoint.getRateAsuransiJiwa();
        call.enqueue(new Callback<ParameterJson.RateAsuransiJiwaCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.RateAsuransiJiwaCallback> call, Response<ParameterJson.RateAsuransiJiwaCallback> response) {
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();
                    rateAsuransiJiwaDataList = new ArrayList<RateAsuransiJiwaModel>();
                    rateAsuransiJiwaList = new ArrayList<String>();

                    for (ParameterJson.RateAsuransiJiwaCallback.RateAsuransiJiwa model : response.body().getL()) {

                        RateAsuransiJiwaModel rateAsuransiJiwaModel = new RateAsuransiJiwaModel();
                        rateAsuransiJiwaModel.setId(model.getId());
                        rateAsuransiJiwaModel.setKeterangan(model.getKeterangan());
                        rateAsuransiJiwaModel.setBatas_atas(model.getBatas_atas());
                        rateAsuransiJiwaModel.setBatas_bawah(model.getBatas_bawah());
                        rateAsuransiJiwaModel.setBiaya_tahun_1(model.getBiaya_tahun_1());
                        rateAsuransiJiwaModel.setBiaya_tahun_2(model.getBiaya_tahun_2());
                        rateAsuransiJiwaModel.setBiaya_tahun_3(model.getBiaya_tahun_3());
                        rateAsuransiJiwaModel.setBiaya_tahun_4(model.getBiaya_tahun_4());

                        rateAsuransiJiwaDataList.add(rateAsuransiJiwaModel);
                        rateAsuransiJiwaList.add(model.getKeterangan());

                        rateAsuransiJiwa.save(model.getId(), model.getKeterangan(), model.getBatas_atas(),
                                model.getBatas_bawah(), model.getBiaya_tahun_1(), model.getBiaya_tahun_2(),
                                model.getBiaya_tahun_3(), model.getBiaya_tahun_4());

//                            Log.e("ket ASJ", rateAsuransiJiwaModel.getKeterangan());
//                            Log.e("batas atas ASJ", rateAsuransiJiwaModel.getBatas_atas().toString());
//                            Log.e("batas bawah ASJ", rateAsuransiJiwaModel.getBatas_bawah().toString());
//                            Log.e("Tahun 1 ASJ", rateAsuransiJiwaModel.getBiaya_tahun_1().toString());
//                            Log.e("Tahun 2 ASJ", rateAsuransiJiwaModel.getBiaya_tahun_2().toString());
//                            Log.e("Tahun 3 ASJ", rateAsuransiJiwaModel.getBiaya_tahun_3().toString());
//                            Log.e("Tahun 4 ASJ", rateAsuransiJiwaModel.getBiaya_tahun_4().toString());

                    }
                    getRateBiaya();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.RateAsuransiJiwaCallback> call, Throwable t) {

            }
        });
    }

    private void getRateBiaya() {


        rateBiaya.deleteAll();

        Call<ParameterJson.RateBiayaCallback> call = endPoint.getRateBiaya();
        call.enqueue(new Callback<ParameterJson.RateBiayaCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.RateBiayaCallback> call, Response<ParameterJson.RateBiayaCallback> response) {
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();
                    rateBiayaDataList = new ArrayList<RateBiayaModel>();
                    rateBiayaList = new ArrayList<String>();

                    for (ParameterJson.RateBiayaCallback.RateBiaya model : response.body().getL()) {

                        RateBiayaModel rateBiayaModel = new RateBiayaModel();
                        rateBiayaModel.setBackendId(model.getId());
                        rateBiayaModel.setTipe(model.getTipe());
                        rateBiayaModel.setKategori(model.getKategori());
                        rateBiayaModel.setProduk(model.getProduk());
                        rateBiayaModel.setNamaBiaya(model.getNama_biaya());
                        rateBiayaModel.setTenor(model.getTenor());
                        rateBiayaModel.setValue(model.getValue());

                        rateBiayaDataList.add(rateBiayaModel);
                        rateBiayaList.add(model.getTipe());

                        rateBiaya.save(model.getId(), model.getTipe(), model.getKategori(), model.getProduk(),
                                model.getNama_biaya(), model.getTenor(), model.getValue());

                    }
                    getKategoriAsuransi();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.RateBiayaCallback> call, Throwable t) {

            }
        });

    }

    private void getKategoriAsuransi() {


        paramKategoriAsuransi.deleteAll();

        Call<ParameterJson.KatAsuransiCallback> call = endPoint.getKategoriAsuransi();
        call.enqueue(new Callback<ParameterJson.KatAsuransiCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.KatAsuransiCallback> call, Response<ParameterJson.KatAsuransiCallback> response) {
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();
                    katAsuransiDataList = new ArrayList<ParamKategoriAsuransiModel>();
                    katAsuransiList = new ArrayList<String>();

                    for (ParameterJson.KatAsuransiCallback.KatAsuransi model : response.body().getL()) {

                        ParamKategoriAsuransiModel paramKategoriAsuransiModel = new ParamKategoriAsuransiModel();
                        paramKategoriAsuransiModel.setBackendId(model.getId());
                        paramKategoriAsuransiModel.setBatasAtas(model.getBatas_atas());
                        paramKategoriAsuransiModel.setBatasBawah(model.getBatas_bawah());
                        paramKategoriAsuransiModel.setValue(model.getValue());


                        katAsuransiDataList.add(paramKategoriAsuransiModel);
                        katAsuransiList.add(model.getValue());

                        paramKategoriAsuransi.save(model.getId(), model.getBatas_atas(), model.getBatas_bawah(),
                                model.getValue());

                    }
                    getPenyusutan();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.KatAsuransiCallback> call, Throwable t) {

            }
        });

    }

    private void getPenyusutan() {


        paramPenyusutan.deleteAll();

        Call<ParameterJson.PenyusutanCallback> call = endPoint.getPenyusutan();
        call.enqueue(new Callback<ParameterJson.PenyusutanCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.PenyusutanCallback> call, Response<ParameterJson.PenyusutanCallback> response) {
                dialog.dismiss();
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();
                    paramPenyusutanDataList = new ArrayList<ParamPenyusutanModel>();
                    paramPenyusutanList = new ArrayList<String>();

                    for (ParameterJson.PenyusutanCallback.ParamPenyusutan model : response.body().getL()) {

                        ParamPenyusutanModel paramPenyusutanModel = new ParamPenyusutanModel();
                        paramPenyusutanModel.setBackendId(model.getId());
                        paramPenyusutanModel.setParameter(model.getParameter());
                        paramPenyusutanModel.setValue(model.getValue());


                        paramPenyusutanDataList.add(paramPenyusutanModel);
                        paramPenyusutanList.add(String.valueOf(model.getParameter()));

                        paramPenyusutan.save(model.getId(), model.getParameter(), model.getValue());
                    }
                    success();
                    } else {
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.PenyusutanCallback> call, Throwable t) {

            }
        });
    }

    private void getParamTenor() {

        parameterTenor.deleteAll();

        Call<ParameterJson.TenorCallback> call = endPoint.getParamTenor();
        call.enqueue(new Callback<ParameterJson.TenorCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.TenorCallback> call, Response<ParameterJson.TenorCallback> response) {
                if (response.body().getRs().equals("OK")) {
//                        dialog.dismiss();
                    paramTenorDataList = new ArrayList<ParameterTenorModel>();
                    paramTenorList = new ArrayList<String>();

                    for (ParameterJson.TenorCallback.ParamTenor model : response.body().getL()) {

                        ParameterTenorModel parameterTenorModel = new ParameterTenorModel();
                        parameterTenorModel.setBackendId(model.getId());
                        parameterTenorModel.setParameter(model.getParameter());
                        parameterTenorModel.setBatasAtas(model.getBatas_atas());
                        parameterTenorModel.setBatasBawah(model.getBatas_bawah());
                        parameterTenorModel.setValue(model.getValue());

                        paramTenorDataList.add(parameterTenorModel);
                        paramTenorList.add(String.valueOf(model.getParameter()));

                        parameterTenor.save(model.getId(), model.getParameter(), model.getBatas_atas(), model.getBatas_bawah(),
                                model.getValue());

                    }
                    getIndicatorHarga();

                } else {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.TenorCallback> call, Throwable t) {
                Log.e("error", t.getLocalizedMessage());
            }
        });
    }

   /* private void callApiSlider(){
        if (! networkConnection.isNetworkConnected()){
            dialog(R.string.errorNoInternetConnection);
        }else {

            sliderdql.deleteAll();
            Call<SliderJson.Callback> call = endPoint.callSlider();
            call.enqueue(new Callback<SliderJson.Callback>() {
                @Override
                public void onResponse(Call<SliderJson.Callback> call, Response<SliderJson.Callback> response) {
                    if (response.body().getRs().equalsIgnoreCase(ResponseStatus.OK.name())){
                        sliderDataList = new ArrayList<Slider>();
                        sliderList = new ArrayList<String>();

                        for (SliderJson.Callback.Slider model : response.body().getL()) {

                            Slider slider = new Slider();
                            slider.setId(model.getId());
                            slider.setName(model.getName());
                            slider.setImage(model.getImage());
                            slider.setLink(model.getLink());
                            slider.setPublish(model.getPublish());
                            slider.setPublish(model.getPackage_name());

                            sliderDataList.add(slider);
                            sliderdql.save(model.getId() , model.getName() , model.getImage() ,
                                    model.getLink(), model.getPublish(), model.getPackage_name());

                        }
                    }else {
                        sliderdql.deleteAll();
                    }
                }

                @Override
                public void onFailure(Call<SliderJson.Callback> call, Throwable t) {

                }
            });
        }
    }*/

    private void callApiSlider(){
        if (! networkConnection.isNetworkConnected()){
            dialog(R.string.errorNoInternetConnection);
        }else {
            sliderdql.deleteAll();

            Call<SliderJson.Callback> call = endPoint.callSlider();
            call.enqueue(new Callback<SliderJson.Callback>() {
                @Override
                public void onResponse(Call<SliderJson.Callback> call, Response<SliderJson.Callback> response) {
                    if (response.body().getRs().equals("OK")){
                        sliderDataList = new ArrayList<Slider>();
                        sliderList = new ArrayList<String>();

                        for (SliderJson.Callback.Slider model : response.body().getL()) {

                            Slider slider = new Slider();
                            slider.setId(model.getId());
                            slider.setName(model.getName());
                            slider.setImage(model.getImage());
                            slider.setLink(model.getLink());
                            slider.setPublish(model.getPublish());
                            slider.setPackage_name(model.getPackage_name());

                            sliderDataList.add(slider);
//                            sliderdql.save(model.getId() , model.getName() , model.getImage() ,
//                                    model.getLink(), model.getPublish(), model.getPackage_name());

                        }
                        skipDisclaimer();
                    }else {
                        skipDisclaimer();
                    }
                }

                @Override
                public void onFailure(Call<SliderJson.Callback> call, Throwable t) {
                    skipDisclaimer();
                }
            });
        }
    }

    private void skipDisclaimer(){
        if (userdata.count() == 0){
            final ParameterJson.OtpParamRequest request = new ParameterJson().new OtpParamRequest();
            request.setUploadBy(getString(R.string.uploadby));

            Call<ParameterJson.OtpParamCallback> call = endPoint2.getOtpParam(request);
            call.enqueue(new Callback<ParameterJson.OtpParamCallback>() {
                @Override
                public void onResponse(Call<ParameterJson.OtpParamCallback> call, Response<ParameterJson.OtpParamCallback> response) {
                    if (response.isSuccessful()) {
                        for (ParameterJson.OtpParamCallback.OtpParam model : response.body().getL()) {
                            bundle.putInt(model.getName(), model.getValue());
                        }
//                        goHome();
                        //Register();
                        getParameterPinjaman();
                    }else {
                        skipDisclaimer();
                    }
                }

                @Override
                public void onFailure(Call<ParameterJson.OtpParamCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            });
        }else{
            getParameterPinjaman();
        }

    }



    private void requestMemberId(){

        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }else{

            final ParameterJson.GetIdRequest request = new ParameterJson().new GetIdRequest();
            request.setTelp(noHp);
            request.setImei(UserImei);

            Call<ParameterJson.GetIdResponse> call = endPoint.getIdCustomer(request);
            call.enqueue(new Callback<ParameterJson.GetIdResponse>() {
                @Override
                public void onResponse(Call<ParameterJson.GetIdResponse> call, Response<ParameterJson.GetIdResponse> response) {
                    if(response.isSuccessful()){
                        if (response.body().getRs().equalsIgnoreCase("OK")){
                            cust_id = response.body().getCustomer_id();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(useridPref, cust_id);
                            editor.apply();
                            getParameterPinjaman();
                        }else if(response.body().getRs().equalsIgnoreCase("IMEI_NOT_MATCH")){
                            dialog.dismiss();
                            dialog(R.string.imeinotmatch);
                        }else if(response.body().getRs().equalsIgnoreCase("IMEI_USED_BY_OTHER")){
                            dialog.dismiss();
                            dialog(R.string.imeiusedbyother);
                        }else{
                            dialog.dismiss();
                            dialog(R.string.errorConnection);
                        }
                    }else{
                        dialog.dismiss();
                        dialog(R.string.errorConnection);

                    }

                }

                @Override
                public void onFailure(Call<ParameterJson.GetIdResponse> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorConnection);
                }
            });

        }
    }


    protected void dialog(int rString) {
        new MaterialDialog.Builder(this)
                .content(rString)
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();
    }
    public  void success(){
        Intent intent = new Intent(this, MainActivityDashboard.class);
        intent.putExtra("sk", "ds");
        startActivity(intent);
        finish();
    }

    private void dialogPermission() {
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.READ_PHONE_STATE
        }, 1);
    }

    protected void dialogPlaystore(int rString) {
        final SQLiteConfig config = new SQLiteConfig(this);
        new MaterialDialog.Builder(this)
                .content(rString)
                .positiveText(R.string.buttonClose)
                .negativeText(R.string.buttonUpdate)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                    public void onNegative(MaterialDialog dialog) {
                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.getGoogleMarket() + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.getGooglePlaystore() + appPackageName)));
                        }
                    }
                })
                .cancelable(true)
                .show();
    }
}
