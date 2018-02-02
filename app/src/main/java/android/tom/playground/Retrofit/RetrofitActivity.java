package android.tom.playground.Retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.R;
import android.tom.playground.Retrofit.gsonmodels.personList;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    private static final String TAG = "RetrofitActivity";

    /**
     * This tuutorial is from
     * https://www.simplifiedcoding.net/retrofit-android-example/
     * @param savedInstanceState
     */

ListView userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        userList = (ListView) findViewById(R.id.listUser);

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Api.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        //creating the api interface
        Api api = retrofit.create(Api.class);


        //now making the call object
        //Here we are using the api method that we created inside the api interface

        Call<personList> call = api.getRandonUsers(5);
        //then finallly we are making the call using enqueue()
        //it takes callback interface as an argument
        //and callback is having two methods onRespnose() and onFailure
        //if the request is successfull we will get the correct response and onResponse will be executed
        //if there is some error we will get inside the onFailure() method

        call.enqueue(new Callback<personList>() {
            @Override
            public void onResponse(Call<personList> call, Response<personList> response) {
                personList personList = response.body();
                PersonListAdapter adapter = new PersonListAdapter(getBaseContext(),personList.getPersonList());
                userList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<personList> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(RetrofitActivity.this, "error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
