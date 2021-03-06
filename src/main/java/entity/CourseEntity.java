package entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "course", schema = "public", catalog = "service")
public class CourseEntity {
    private int id;
    private String status;
    private String structure;
    private String uuid;
    private String nameCourse;
    private AuthInfEntity authById;
    private Set<AuthInfEntity> authAcsById;
    private Set<AuthInfEntity> authRCById;
    private Integer category;
    private String dateCreate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "date_create")
    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Basic
    @Column(name = "name_course")
    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    @Basic
    @Column(name = "structure")
    public String getStructure() {
        return structure;
    }

    public void setStructure(String strucrure) {
        this.structure = strucrure;
    }

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @ManyToOne
    public AuthInfEntity getAuthById() {
        return authById;
    }

    public void setAuthById(AuthInfEntity authById) {
        this.authById = authById;
    }

    @ManyToMany(mappedBy = "courseAcsById")
    public Set<AuthInfEntity> getAuthAcsById() {
        return authAcsById;
    }

    public void setAuthAcsById(Set<AuthInfEntity> authAcsById) {
        this.authAcsById = authAcsById;
    }

    @ManyToMany(mappedBy = "coursesFById")
    public Set<AuthInfEntity> getAuthRCById() {
        return authRCById;
    }

    public void setAuthRCById(Set<AuthInfEntity> authRCById) {
        this.authRCById = authRCById;
    }

    @Basic
    @Column(name = "category")
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
