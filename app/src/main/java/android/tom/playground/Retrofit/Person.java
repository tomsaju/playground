package android.tom.playground.Retrofit;

import android.graphics.Picture;
import android.tom.playground.Retrofit.gsonmodels.name;
import android.tom.playground.Retrofit.gsonmodels.picture;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

/**
 * Created by tom.saju on 2/2/2018.
 */

public class Person {

    name name;
    String email;
    picture picture;



    public android.tom.playground.Retrofit.gsonmodels.name getName() {
        return name;
    }

    public void setName(android.tom.playground.Retrofit.gsonmodels.name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public android.tom.playground.Retrofit.gsonmodels.picture getPicture() {
        return picture;
    }

    public void setPicture(android.tom.playground.Retrofit.gsonmodels.picture picture) {
        this.picture = picture;
    }
}
