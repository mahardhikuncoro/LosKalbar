package base.location.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ADMINSMMF on 1/15/2018.
 */

public interface LocationApi {

    @GET("mobiletracking/insert/{mobileUserId}/{latitude}/{longitude}/{heading}/{actOrdinal}/{userType}")
    Call<SendLocationJson.Callback> sendLocation(@Path("mobileUserId") Long mobileUserId,
                                                 @Path("latitude") String latitude, @Path("longitude") String longitude, @Path("heading") String heading,
                                                 @Path("actOrdinal") Integer actOrdinal, @Path("userType") Integer userType);


    @GET("mobileconfig/select/{version}/{key}")
    Call<MobileConfigJson.Callback> config(@Path("version") Integer version, @Path("key") String key);


}
