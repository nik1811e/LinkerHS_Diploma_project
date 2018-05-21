package entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "auth_inf", schema = "public", catalog = "service")
public class AuthInfEntity {
    private int id;
    private String email;
    private String login;
    private String password;
    private String uuid;
    private String role;
    private String request;
    private Set<CourseEntity> coursesById;
    private Set<CourseEntity> courseAcsById;
    private Set<CourseEntity> coursesFById;
    private String dateReg;
    private String fName;
    private String lName;
    private String dDay;
    private String about;


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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "f_name")
    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    @Basic
    @Column(name = "l_name")
    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    @Basic
    @Column(name = "bday")
    public String getBDay() {
        return dDay;
    }

    public void setBDay(String dDay) {
        this.dDay = dDay;
    }

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @Basic
    @Column(name = "about")
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "course_request")
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @OneToMany(mappedBy = "authById")
    public Set<CourseEntity> getCoursesById() {
        return coursesById;
    }

    public void setCoursesById(Set<CourseEntity> coursesById) {
        this.coursesById = coursesById;
    }

    @ManyToMany
    @JoinTable(name = "access", catalog = "service", schema = "public", joinColumns = @JoinColumn(name = "id_auth", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_course", referencedColumnName = "id", nullable = false))
    public Set<CourseEntity> getCourseAcsById() {
        return courseAcsById;
    }

    public void setCourseAcsById(Set<CourseEntity> courseAcsById) {
        this.courseAcsById = courseAcsById;
    }

    @ManyToMany
    @JoinTable(name = "favorite_course", catalog = "service", schema = "public", joinColumns = @JoinColumn(name = "id_auth", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_course", referencedColumnName = "id", nullable = false))
    public Set<CourseEntity> getCoursesFById() {
        return coursesFById;
    }

    public void setCoursesFById(Set<CourseEntity> coursesFById) {
        this.coursesFById = coursesFById;
    }

    @Basic
    @Column(name = "date_reg")
    public String getDateReg() {
        return dateReg;
    }

    public void setDateReg(String dateReg) {
        this.dateReg = dateReg;
    }
}
