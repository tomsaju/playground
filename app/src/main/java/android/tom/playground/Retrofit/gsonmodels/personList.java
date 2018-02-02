package android.tom.playground.Retrofit.gsonmodels;

import android.tom.playground.Retrofit.Person;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tom.saju on 2/2/2018.
 */

public class personList {
    @SerializedName("results")
    List<Person> personList;

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
