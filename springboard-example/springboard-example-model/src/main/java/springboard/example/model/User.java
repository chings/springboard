package springboard.example.model;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;
import java.util.Date;

public class User extends Role implements Serializable {

    public enum Status implements IEnum, Serializable {
        ACTIVE(0), DISABLED(-1);

        int value;

        Status(int value) {
            this.value = value;
        }

        @Override
        public Serializable getValue() {
            return value;
        }
    }

    Status status;
    String username;
    String password;
    Date lastLoggedInTime;
    String lastLoggedInAddr;

    public User() {
        super();
        type = Type.USER;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", createdTime=" + createdTime +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastLoggedInTime=" + lastLoggedInTime +
                ", lastLoggedInAddr='" + lastLoggedInAddr + '\'' +
                '}';
    }

}
