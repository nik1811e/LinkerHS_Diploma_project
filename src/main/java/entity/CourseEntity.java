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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseEntity that = (CourseEntity) o;

        if (id != that.id) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (structure != null ? !structure.equals(that.structure) : that.structure != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (structure != null ? structure.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
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
