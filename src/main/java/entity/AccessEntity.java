package entity;

import javax.persistence.*;

@Entity
@Table(name = "access", schema = "public", catalog = "service")
public class AccessEntity {
    private int idCourse;
    private int idAuth;
    private int id;

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
    @Column(name = "id_course")
    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    @Basic
    @Column(name = "id_auth")
    public int getIdAuth() {
        return idAuth;
    }

    public void setIdAuth(int idAuth) {
        this.idAuth = idAuth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessEntity that = (AccessEntity) o;

        if (idCourse != that.idCourse) return false;
        if (idAuth != that.idAuth) return false;
        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCourse;
        result = 31 * result + idAuth;
        result = 31 * result + id;
        return result;
    }
}
