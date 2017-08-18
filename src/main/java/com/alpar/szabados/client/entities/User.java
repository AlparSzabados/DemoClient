package com.alpar.szabados.client.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
public class User implements Serializable {
    private String userName;
    private String encodedPassword;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String encodedPassword) {
        this.userName = userName;
        this.encodedPassword = encodedPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    @Override
    public String toString() {
        return String.format("User{userName='%s', encodedPassword='%s'}", userName, encodedPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName)
                && Objects.equals(encodedPassword, user.encodedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, encodedPassword);
    }
}
