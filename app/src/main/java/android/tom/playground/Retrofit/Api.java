package android.tom.playground.Retrofit;

import android.tom.playground.Retrofit.gsonmodels.personList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tom.saju on 2/2/2018.
 */

public interface Api {
    String BASE_URL = "https://randomuser.me/";

    @GET("api/")
    Call<personList> getRandonUsers(@Query("results") int results);

}
