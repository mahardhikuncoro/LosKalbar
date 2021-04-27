package base.network;

import java.util.List;
import java.util.Map;

import base.endpoint.UploadImageJson;
import base.location.network.BaseNetworkCallback;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by christian on 4/6/18.
 */

public interface EndPoint {

    //SIT

    @GET("rate/indicator_harga.php")
    Call<ParameterJson.IndicatorHargaCallback> getIndicatorHarga();

    @GET("rate/indicator_tahun.php")
    Call<ParameterJson.IndicatorTahunCallback> getIndicatorTahun();

    @GET("rate/rate_bunga.php")
    Call<ParameterJson.RateBungaCallback> getRateBunga();

    @GET("rate/rate_asuransi.php")
    Call<ParameterJson.RateAsuransiCallback> getRateAsuransiMobil();

    @GET("rate/rate_asuransi_motor.php")
    Call<ParameterJson.RateAsuransiMotorCallback> getRateAsuransiMotor();

    @GET("rate/rate_asuransi_jiwa.php")
    Call<ParameterJson.RateAsuransiJiwaCallback> getRateAsuransiJiwa();

    @GET("rate/rate_biaya.php")
    Call<ParameterJson.RateBiayaCallback> getRateBiaya();

    @GET("parameter/parameter_pinjaman.php")
    Call<ParameterJson.PinjamanCallback> getParameterPinjaman();

    @GET("parameter/parameter_kategori_asuransi.php")
    Call<ParameterJson.KatAsuransiCallback> getKategoriAsuransi();

    @GET("parameter/parameter_penyusutan.php")
    Call<ParameterJson.PenyusutanCallback> getPenyusutan();

    @GET("parameter/parameter_tenor.php")
    Call<ParameterJson.TenorCallback> getParamTenor();

    @GET("parameter/parameter_pinjaman_extended.php")
    Call<ParameterJson.PinjamanExtendedCallback> getPinjamanExtended();


    @GET("slider.php")
    Call<SliderJson.Callback> callSlider();


    @POST("param.php")
    Call<ParameterJson.OtpParamCallback> getOtpParam(@Body ParameterJson.OtpParamRequest body);


    @POST("aplikasi-online-mobile.php")
    Call<ParameterJson.aplikasiOnlineCallback> aplikasionline(@Body ParameterJson.aplikasiOnlineRequest body);

    @POST("mobile_version.php")
    Call<ParameterJson.VersionCallback> checkVersion(@Body ParameterJson.VersionRequest body);

    @POST("ticket/request_ticket-okt18.php")
    Call<ParameterJson.RentalCallback> requestTicket(@Body ParameterJson.RentalRequest body);

    @POST("ticket/get_ticket.php")
    Call<ParameterJson.rentalListCallback> requestRentalList(@Body ParameterJson.rentalListRequest body);

    @POST("ticket/check_ticket_response.php")
    Call<ParameterJson.RentalDetailCallback> requestRentalListDetail(@Body ParameterJson.RentalDetailRequest body);


    @POST("ticket/hide.php")
    Call<ParameterJson.HideResponse> hide(@Body ParameterJson.HideRequest body);

    @POST("get_customerid.php")
    Call<ParameterJson.GetIdResponse> getIdCustomer(@Body ParameterJson.GetIdRequest body);

    //UPDATE WITH LOGIN


    @POST("customer/reset_password.php")
    Call<ParameterJson.changePassResponse> changePassEndpoint(@Body ParameterJson.changePassRequest body);

    @POST("ticket/listppk.php")
    Call<ParameterJson.listPPKCallback> listPPK(@Body ParameterJson.listPPKrequest body);


    @POST("push/register.php")
    Call<PushNotificationJson.registerTokenIdCallback> registerTokenId(@Body PushNotificationJson.registerTokenIdRequest body);

    @POST("push/unregister.php")
    Call<PushNotificationJson.registerTokenIdCallback> unRegisterTokenId(@Body PushNotificationJson.registerTokenIdRequest body);


    //LOS KALBAR


    @POST("userLogin")
    Call<LoginJson.UserVerifyCallback> getVerifyUser(@Body LoginJson.LoginRequest body);

    @POST("userLogin")
    Call<LoginJson.loginAutenticationCallback> getAutentication(@Body LoginJson.LoginRequest body);

    @POST("getMenuAccess")
    Call<LoginJson.getmenuCallback> getMenuAcces(@Header("Authorization") String authKey, @Body LoginJson.LoginRequest body);

    @POST("getInbox")
    Call<TaskListJson.TasklistCallback> getInbox(@Header("Authorization") String authKey, @Body TaskListJson.TasklistRequest body);

    @POST("getDataMaster")
    Call<FormJson.CallbackForm> getDataMaster(@Header("Authorization") String authKey, @Body FormJson.RequestForm body);

    @POST("getDataMaster")
    Call<FormCascadingJson.CallbackForm> getDataMasterCascading(@Header("Authorization") String authKey, @Body FormCascadingJson.RequestForm body);

    @POST("getData")
    Call<RetreiveJson.RetreiveCallback> getDataRetreive(@Header("Authorization") String authKey, @Body RetreiveJson.RetreiveRequest body);

    @POST("changePassword")
    Call<ChangePassJson.ChangePassCallback> changePassword(@Header("Authorization") String authKey, @Body ChangePassJson.ChangePassRequest body);

    @POST("setData")
    Call<SetDataJson.SetDataCallback> setData(@Header("Authorization") String authKey, @Body SetDataJson.SetDataRequest body);

    @POST("updateStatus")
    Call<SetDataJson.SetDataCallback> updateStatus(@Header("Authorization") String authKey, @Body SetDataJson.SetDataRequest body);

    @Multipart
    @POST("uploadFiles")
    Call<UploadImageJson.Callback> uploadFile(@Header("Authorization") String authKey,
                                              @PartMap() Map<String, RequestBody> partMap,
                                              @Part() MultipartBody.Part image);

    @POST("deleteData")
    Call<SetDataJson.SetDataCallback> deleteData(@Header("Authorization") String authKey, @Body SetDataJson.SetDataRequest body);

}
