package ch.uzh.ifi.hase.soprafs23.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
public class  User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="register_time")
    private Date register_time;
    @Column(name="has_new")
    private Boolean hasNew;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }

    public Boolean getHasNew() {
        return hasNew;
    }

    public void setHasNew(Boolean hasNew) {
        this.hasNew = hasNew;
    }
}
