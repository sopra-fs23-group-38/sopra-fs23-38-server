package ch.uzh.ifi.hase.soprafs23.rest.dto;

import java.util.Date;

public class UserPostDTO {

  private String name;

  private String password;

  private String username;

  private Date birthday;

  public String getPassword() {
    return password;
  }

  public void setPassword(String name) {
    this.password = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
