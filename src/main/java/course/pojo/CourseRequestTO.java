package course.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseRequestTO {

    @SerializedName("uuid_course_owner")
    @Expose
    private String uuidCourseOwner;
    @SerializedName("request")
    @Expose
    private List<RequestTO> request = null;

    public String getUuidCourseOwner() {
        return uuidCourseOwner;
    }

    public void setUuidCourseOwner(String uuidCourseOwner) {
        this.uuidCourseOwner = uuidCourseOwner;
    }

    public List<RequestTO> getRequest() {
        return request;
    }

    public void setRequest(List<RequestTO> request) {
        this.request = request;
    }

}