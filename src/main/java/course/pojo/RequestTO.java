package course.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestTO {
    @SerializedName("uuid_auth")
    @Expose
    private String uuidAuth;
    @SerializedName("uuid_course")
    @Expose
    private String uuidCourse;

    public String getUuidAuth() {
        return uuidAuth;
    }

    public void setUuidAuth(String uuidAuth) {
        this.uuidAuth = uuidAuth;
    }

    public String getUuidCourse() {
        return uuidCourse;
    }

    public void setUuidCourse(String uuidCourse) {
        this.uuidCourse = uuidCourse;
    }
}
