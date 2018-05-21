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

}
