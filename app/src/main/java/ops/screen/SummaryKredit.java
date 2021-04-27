package ops.screen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.DecimalFormat;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.ParameterJson;
import base.screen.BaseDialogActivity;
import base.sqlite.Userdata;
import base.sqlite.SQLiteConfigKu;
import base.utils.NumberSeparator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.smmf.mobile.R;
import ops.SimulasiKredit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by christian on 4/12/18.
 */

public class SummaryKredit extends BaseDialogActivity {

    public static final String SUMMARY_DANA = "SUMMARY_DANA";
    public static final String SUMMARY_AGUNAN = "SUMMARY_AGUNAN";
    public static final String SUMMARY_TAHUNKENDARAAN = "SUMMARY_TAHUNKENDARAAN";
    public static final String SUMMARY_CABANGSMMF = "SUMMARY_CABANGSMMF";
    public static final String SUMMARY_KOTA = "SUMMARY_KOTA";
    public static final String SUMMARY_WILAYAH = "SUMMARY_WILAYAH";
    public static final String SUMMARY_DP = "SUMMARY_DP";
    public static final String SUMMARY_PAKET = "SUMMARY_PAKET";
    public static final String SUMMARY_TENOR = "SUMMARY_TENOR";
    public static final String SUMMARY_ANGSURAN = "SUMMARY_ANGSURAN";
    public static final String SUMMARY_SUKUBUNGA = "SUMMARY_SUKUBUNGA";
    public static final String SUMMARY_POKOKHUTANG = "SUMMARY_POKOKHUTANG";
    public static final String SUMMARY_PENCAIRAN = "SUMMARY_PENCAIRAN";
    public static final String SUMMARY_TDP = "SUMMARY_TDP";
    public static final String SUMMARY_POKOKHUTANG_MOTOR = "SUMMARY_POKOKHUTANG_MOTOR";
    public static final String SUMMARY_DISCLAIMER = "SUMMARY_DISCLAIMER";
    public static final String KETAGUNAN = "KETAGUNAN";
    public static final String NOREFERAL = "NOREFERAL";
    public static final String KETREFERAL = "KETREFERAL";

    private Double summaryDana;
    private String summaryAgunan;
    private Integer summaryTahunKendaraan;
    private Long summaryCabangSMMF;
    private String summaryKota;
    private Integer summaryWilayah;
    private Double summaryDp;
    private String summaryPaket;
    private Integer summaryTenor;
    private Double summaryAngsuran;
    private Double summarySukuBunga;
    private Double summaryPokokHutang;
    private Double summaryPokokHutangMotor;
    private Double summaryPencairan;
    private Double summaryTdp;
    private String summaryDisclaimer;
    private String ketagunan;
    private String noreferal;
    private String ketreferal;

    private SimulasiKredit simulasiKredit;
    private KreditModel kreditModel;

    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;
    private EndPoint endPoint;
    private Userdata userdata;

    private MaterialDialog dialog;

    SharedPreferences sharedpreferences;
    public static final String useridPref = "userid";
    public static final String simaskupref = "simaskupref";
    private String cust_id, UserImei="";



    @BindView(R.id.tvTipeKredit)
    TextView tvTipeKredit;

    @BindView(R.id.tvTipeProduk)
    TextView tvTipeProduk;

    @BindView(R.id.tvPokokHutang)
    TextView tvPokokHutang;

    @BindView(R.id.tvTenorPilihan)
    TextView tvTenorPilihan;

    @BindView(R.id.tvAngsuranPerBulan)
    TextView tvAngsuranPerBulan;

    @BindView(R.id.pencairan)
    TextView pencairan;

    @BindView(R.id.tdp)
    TextView tdp;

    @BindView(R.id.tvTdp)
    TextView tvTdp;

    @BindView(R.id.tvPencairan)
    TextView tvPencairan;

    @BindView(R.id.noteSummary)
    TextView noteSummary;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_activity);
        ButterKnife.bind(this);

        sqLiteConfigKu = new SQLiteConfigKu(this);
        networkConnection = new NetworkConnection(this);
        userdata = new Userdata(this);
        sharedpreferences = getSharedPreferences(simaskupref, Context.MODE_PRIVATE);
        cust_id = sharedpreferences.getString(useridPref, "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint = retrofit.create(EndPoint.class);


        kreditModel = new KreditModel();
        simulasiKredit = new SimulasiKredit();


        summaryDana = getIntent().getExtras().getDouble(SUMMARY_DANA);
        summaryAgunan = getIntent().getExtras().getString(SUMMARY_AGUNAN);
        summaryTahunKendaraan = getIntent().getExtras().getInt(SUMMARY_TAHUNKENDARAAN);
        summaryCabangSMMF = getIntent().getExtras().getLong(SUMMARY_CABANGSMMF);
        summaryKota = getIntent().getExtras().getString(SUMMARY_KOTA);
        summaryWilayah = getIntent().getExtras().getInt(SUMMARY_WILAYAH);
        summaryDp = getIntent().getExtras().getDouble(SUMMARY_DP);
        summaryPaket = getIntent().getExtras().getString(SUMMARY_PAKET);
        summaryTenor = getIntent().getExtras().getInt(SUMMARY_TENOR);
        summaryPokokHutang = getIntent().getExtras().getDouble(SUMMARY_POKOKHUTANG);
        summaryPokokHutangMotor = getIntent().getExtras().getDouble(SUMMARY_POKOKHUTANG_MOTOR);
        summaryAngsuran = getIntent().getExtras().getDouble(SUMMARY_ANGSURAN);
        summaryPencairan = getIntent().getExtras().getDouble(SUMMARY_PENCAIRAN);
        summaryTdp = getIntent().getExtras().getDouble(SUMMARY_TDP);
        summaryDisclaimer = getIntent().getExtras().getString(SUMMARY_DISCLAIMER);
        ketagunan = getIntent().getExtras().getString(KETAGUNAN);
        noreferal = getIntent().getExtras().getString(NOREFERAL);
        ketreferal = getIntent().getExtras().getString(KETREFERAL);


        DecimalFormat format1 = new DecimalFormat("#");
        format1.setMaximumFractionDigits(0);

        if (summaryPaket.equalsIgnoreCase("leaseback")) {
            pencairan.setVisibility(View.VISIBLE);
            tvPencairan.setVisibility(View.VISIBLE);

            if (summaryAgunan.equalsIgnoreCase("motor")){
                tvPokokHutang.setText(String.valueOf("Rp. " + NumberSeparator.split(format1.format(summaryPokokHutangMotor))));
            } else if (summaryAgunan.equalsIgnoreCase("mobil")){
                tvPokokHutang.setText(String.valueOf("Rp. "+ NumberSeparator.split(format1.format(summaryPokokHutang))));
            }
            tvPencairan.setText(String.valueOf("Rp. "+ NumberSeparator.split(format1.format(summaryPencairan))));

        } else if (summaryPaket.equalsIgnoreCase("jualbeli")){
            tdp.setVisibility(View.VISIBLE);
            tvTdp.setVisibility(View.VISIBLE);
            if (summaryAgunan.equalsIgnoreCase("mobil")){
                tvPokokHutang.setText(String.valueOf("Rp. "+ NumberSeparator.split(format1.format(summaryPokokHutang))));
            }

            tvTdp.setText(String.valueOf("Rp. "+ NumberSeparator.split(format1.format(summaryTdp))));
        }

        tvTipeKredit.setText(summaryPaket);
        tvTipeProduk.setText(summaryAgunan);
        tvTenorPilihan.setText(String.valueOf(summaryTenor));
        tvAngsuranPerBulan.setText(String.valueOf("Rp. "+ NumberSeparator.split(format1.format(summaryAngsuran))));

        noteSummary.setText(summaryDisclaimer);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            dialogPermission();
        }else{
            UserImei = telephonyManager.getDeviceId();
        }
    }

    @OnClick(R.id.buttonLanjutSubmit)
    public void requestMemberId(){

        dialog  = new MaterialDialog.Builder(this)
                .content(R.string.loading)
                .progress(true, 0)
                .cancelable(false)
                .show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }else{

            final ParameterJson.GetIdRequest request = new ParameterJson().new GetIdRequest();
            request.setTelp(userdata.select().getUserid());
            request.setImei(UserImei);

            Call<ParameterJson.GetIdResponse> call = endPoint.getIdCustomer(request);
            call.enqueue(new Callback<ParameterJson.GetIdResponse>() {
                @Override
                public void onResponse(Call<ParameterJson.GetIdResponse> call, Response<ParameterJson.GetIdResponse> response) {
                    if(response.isSuccessful()){
                        dialog.dismiss();
                        if (response.body().getRs().equalsIgnoreCase("OK")){
                            cust_id = response.body().getCustomer_id();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(useridPref, cust_id);
                            editor.apply();
                            submitData();
                        }else if(response.body().getRs().equalsIgnoreCase("IMEI_NOT_MATCH")){
                            dialog(R.string.imeinotmatch);
                        }else if(response.body().getRs().equalsIgnoreCase("IMEI_USED_BY_OTHER")){
                            cust_id = response.body().getCustomer_id();
                            dialog(R.string.imeiusedbyother);
                        }else{
                            dialog(R.string.errorConnection);
                        }
                    }else{
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


    private void submitData(){

        dialog = new MaterialDialog.Builder(this)
                .content(R.string.loading)
                .progress(true, 0)
                .cancelable(false)
                .show();

        String noTelp = userdata.select().getUserid();


        final ParameterJson.aplikasiOnlineRequest request = new ParameterJson().new aplikasiOnlineRequest();
        request.setVersion(sqLiteConfigKu.getServerVersion());
        request.setPaket(summaryPaket);
        request.setProduk(summaryAgunan);
        request.setHarga(summaryDana);
        request.setTahunkendaraan(summaryTahunKendaraan);
        request.setCabang_smmf(summaryCabangSMMF);
//        request.setTelp("081945000803");
        request.setTelp(noTelp);
        request.setTipe_kendaraan(ketagunan);
        request.setTenor(summaryTenor);
        request.setDp(summaryDp);
        request.setReferal_mkt(noreferal);
        request.setKeterangan_referal_mkt(ketreferal);
        request.setCustomer_id(cust_id);

        Call<ParameterJson.aplikasiOnlineCallback> call = endPoint.aplikasionline(request);
        call.enqueue(new Callback<ParameterJson.aplikasiOnlineCallback>() {
            @Override
            public void onResponse(Call<ParameterJson.aplikasiOnlineCallback> call, Response<ParameterJson.aplikasiOnlineCallback> response) {
                dialog.dismiss();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        if (response.body().getResponseStatus().equalsIgnoreCase("OK")){
                            success();
                        }else if(response.body().getResponseStatus().equalsIgnoreCase("PHONE_NUMBER_NOT_FOUND")){
                            dialog(R.string.phonenotfound);
                        }else if (response.body().getResponseStatus().equals("INCOMPATIBLE_VERSION")) {
                            dialogPlaystore(R.string.errorIncompatibleVersion);
                        }else if (response.body().getResponseStatus().equals("UNAUTHORIZED")) {
                            dialog(R.string.registerUnauthorized);
                        }else{
                            dialog(R.string.errorConnection);
                        }
                    }else{
                        dialog(R.string.errorConnection);
                    }
                }else{
                    dialog(R.string.errorConnection);
                }
            }

            @Override
            public void onFailure(Call<ParameterJson.aplikasiOnlineCallback> call, Throwable t) {

            }
        });


//        Intent intent = new Intent(this, SubmitDataPribadi.class);
////        Log.e("ke submit data pribadi", "ini");
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra(SubmitDataPribadi.TENOR, summaryTenor);
//        intent.putExtra(SubmitDataPribadi.DANA, summaryDana);
//        intent.putExtra(SubmitDataPribadi.AGUNAN, summaryAgunan);
//        intent.putExtra(SubmitDataPribadi.TAHUNKENDARAAN, summaryTahunKendaraan);
//        intent.putExtra(SubmitDataPribadi.CABANGSMMF, summaryCabangSMMF);
//        intent.putExtra(SubmitDataPribadi.KOTA, summaryKota);
//        intent.putExtra(SubmitDataPribadi.WILAYAH, summaryWilayah);
//        intent.putExtra(SubmitDataPribadi.DP, summaryDp);
//        intent.putExtra(SubmitDataPribadi.PAKET, summaryPaket);
//
//        startActivity(intent);
    }

    private void success(){
        Intent intent = new Intent(this, MainActivityDashboard.class);
        intent.putExtra("sk", "sk");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
