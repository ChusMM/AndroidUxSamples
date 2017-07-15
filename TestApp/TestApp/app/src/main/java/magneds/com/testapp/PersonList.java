package magneds.com.testapp;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jesusmanuelmunoz on 14/11/16.
 */

public class PersonList {
    @SerializedName("person")
    public List<Person> persons;

    public static PersonList fromJson(String json) {
        return new Gson().fromJson(json, PersonList.class);
    }

    public static class Person {
        @SerializedName("firstName")
        public String firstName;

        @SerializedName("infix")
        public String infix;

        @SerializedName("lastName")
        public String lastName;

        @SerializedName("age")
        public int age;

        @SerializedName("link")
        public String link;
    }
}
