package entity;

import javax.persistence.*;

@Entity
@Table(name = "following", schema = "public", catalog = "service")
public class FollowingEntity {
    private int id;
    private Integer idAuth;
    private Integer idFollowing;

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
    public Integer getIdAuth() {
        return idAuth;
    }

    public void setIdAuth(Integer idAuth) {
        this.idAuth = idAuth;
    }

    @Basic
    @Column(name = "id_following")
    public Integer getIdFollowing() {
        return idFollowing;
    }

    public void setIdFollowing(Integer idFollowing) {
        this.idFollowing = idFollowing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowingEntity that = (FollowingEntity) o;

        if (id != that.id) return false;
        if (idAuth != null ? !idAuth.equals(that.idAuth) : that.idAuth != null) return false;
        if (idFollowing != null ? !idFollowing.equals(that.idFollowing) : that.idFollowing != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (idAuth != null ? idAuth.hashCode() : 0);
        result = 31 * result + (idFollowing != null ? idFollowing.hashCode() : 0);
        return result;
    }
}
