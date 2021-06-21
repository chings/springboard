package springboard.example.event;

import java.io.Serializable;
import java.util.Date;

public class LoggedInEvent implements Serializable {

    Long userId;
    String username;
    Date loggedInTime;
    String loggedInAddr;

    public Long getUserId() {
        return userId;
    }

    public LoggedInEvent setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoggedInEvent setUsername(String username) {
        this.username = username;
        return this;
    }

    public Date getLoggedInTime() {
        return loggedInTime;
    }

    public LoggedInEvent setLoggedInTime(Date loggedInTime) {
        this.loggedInTime = loggedInTime;
        return this;
    }

    public String getLoggedInAddr() {
        return loggedInAddr;
    }

    public LoggedInEvent setLoggedInAddr(String loggedInAddr) {
        this.loggedInAddr = loggedInAddr;
        return this;
    }

    @Override
    public String toString() {
        return "LoggedInEvent{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", loggedInTime=" + loggedInTime +
                ", loggedInAddr='" + loggedInAddr + '\'' +
                '}';
    }

}
