package course.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResourceTO {

    @SerializedName("uuid_auth")
    @Expose
    private String uuidAuth;
    @SerializedName("uuid_resource")
    @Expose
    private String uuidResource;
    @SerializedName("uuid_section")
    @Expose
    private String uuidSection;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("description_resource")
    @Expose
    private String descriptionResource;
    @SerializedName("category_link")
    @Expose
    private int category_link;

    public String getUuidAuth() {
        return uuidAuth;
    }

    public void setUuidAuth(String uuidAuth) {
        this.uuidAuth = uuidAuth;
    }

    public String getUuidSection() {
        return uuidSection;
    }

    public void setUuidSection(String uuidSection) {
        this.uuidSection = uuidSection;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionResource() {
        return descriptionResource;
    }

    public void setDescriptionResource(String descriptionResource) {
        this.descriptionResource = descriptionResource;
    }

    public int getCategory_link() {
        return category_link;
    }

    public void setCategory_link(int category_link) {
        this.category_link = category_link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUuidResource() {
        return uuidResource;
    }

    public void setUuidResource(String uuidResource) {
        this.uuidResource = uuidResource;
    }
}