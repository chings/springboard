package springboard.example.model;

import java.io.Serializable;
import java.util.Date;

public class LoginEvent implements Serializable {

    Long userId;
    String username;
    Date loggedInTime;
    String loggedInAddr;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoggedInTime() {
        return loggedInTime;
    }

    public void setLoggedInTime(Date loggedInTime) {
        this.loggedInTime = loggedInTime;
    }

    public String getLoggedInAddr() {
        return loggedInAddr;
    }

    public void setLoggedInAddr(String loggedInAddr) {
        this.loggedInAddr = loggedInAddr;
    }

    @Override
    public String toString() {
        return "LoginEvent{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", loggedInTime=" + loggedInTime +
                ", loggedInAddr='" + loggedInAddr + '\'' +
                '}';
    }

}
