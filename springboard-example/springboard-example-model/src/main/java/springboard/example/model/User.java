package springboard.example.model;

import java.io.Serializable;
import java.util.Date;

public class User extends Role implements Serializable {

    String username;
    String password;
    Date lastLoggedInTime;
    String lastLoggedInAddr;

    public User() {
        type = Type.USER;
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

    public Date getLastLoggedInTime() {
        return lastLoggedInTime;
    }

    public void setLastLoggedInTime(Date lastLoggedInTime) {
        this.lastLoggedInTime = lastLoggedInTime;
    }

    public String getLastLoggedInAddr() {
        return lastLoggedInAddr;
    }

    public void setLastLoggedInAddr(String lastLoggedInAddr) {
        this.lastLoggedInAddr = lastLoggedInAddr;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastLoggedInTime=" + lastLoggedInTime +
                ", lastLoggedInAddr='" + lastLoggedInAddr + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

}
