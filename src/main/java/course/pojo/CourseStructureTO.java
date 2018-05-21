package course.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseStructureTO {

    @SerializedName("uuid_user")
    @Expose
    private String uuidUser;
    @SerializedName("name_course")
    @Expose
    private String nameCourse;
    @SerializedName("description_course")
    @Expose
    private String descriptionCourse;
    @SerializedName("date_create")
    @Expose
    private String dateCreate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("section")
    @Expose
    private List<SectionTO> section = null;

    public String getUuidUser() {
        return uuidUser;
    }

    public void setUuidUser(String uuidUser) {
        this.uuidUser = uuidUser;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getDescriptionCourse() {
        return descriptionCourse;
    }

    public void setDescriptionCourse(String descriptionCourse) {
        this.descriptionCourse = descriptionCourse;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SectionTO> getSection() {
        return section;
    }

    public void setSection(List<SectionTO> section) {
        this.section = section;
    }

}