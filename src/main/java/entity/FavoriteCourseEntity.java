package entity;

import javax.persistence.*;

@Entity
@Table(name = "favorite_course", schema = "public", catalog = "service")
public class FavoriteCourseEntity {
    private int idAuth;
    private int id;
    private int idCourse;

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
    @Column(name = "id_auth")
    public int getIdAuth() {
        return idAuth;
    }

    public void setIdAuth(int idAuth) {
        this.idAuth = idAuth;
    }



    @Basic
    @Column(name = "id_course")
    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoriteCourseEntity that = (FavoriteCourseEntity) o;

        if (idAuth != that.idAuth) return false;
        if (id != that.id) return false;
        if (idCourse != that.idCourse) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAuth;
        result = 31 * result + id;
        result = 31 * result + idCourse;
        return result;
    }
}
