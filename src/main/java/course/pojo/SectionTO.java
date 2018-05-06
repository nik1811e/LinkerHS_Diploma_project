package course.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionTO {

    @SerializedName("uuid_course")
    @Expose
    private String uuidCourse;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uuid_section")
    @Expose
    private String uuidSection;
    @SerializedName("description_section")
    @Expose
    private String descriptionSection;
    @SerializedName("date_last_update")
    @Expose
    private String dateLastUpdate;
    @SerializedName("resource")
    @Expose
    private List<ResourceTO> resource = null;

    public String getUuidCourse() {
        return uuidCourse;
    }

    public void setUuidCourse(String uuidCourse) {
        this.uuidCourse = uuidCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuidSection() {
        return uuidSection;
    }

    public void setUuidSection(String uuidSection) {
        this.uuidSection = uuidSection;
    }

    public String getDescriptionSection() {
        return descriptionSection;
    }

    public void setDescriptionSection(String descriptionSection) {
        this.descriptionSection = descriptionSection;
    }

    public String getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(String dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public List<ResourceTO> getResource() {
        return resource;
    }

    public void setResource(List<ResourceTO> resource) {
        this.resource = resource;
    }

}