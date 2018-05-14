package api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthRegAPITO {
    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("first_name")
    @Expose
    private String fname;

    @SerializedName("last_name")
    @Expose
    private String lname;

    @SerializedName("birth_day")
    @Expose
    private String bday;

    public String getLogin() {
        return login;
    }
    public String getEmail () {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getBday() {
        return bday;
    }
}
