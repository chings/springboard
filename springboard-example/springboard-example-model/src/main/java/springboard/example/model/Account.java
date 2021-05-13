package springboard.example.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value="account", resultMap="accountResultMap")
public class Account implements Serializable {

    public enum Status implements IEnum<Integer>, Serializable {
        ACTIVE(0), DISABLED(-1);

        @EnumValue
        int value;

        Status(int value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() { return String.valueOf(value); }

    }

    @TableId
    String username;
    Status status;
    Long userId;
    String password;
    String encodedPassword;
    Date createdTime;
    Date updatedTime;
    Date lastLoggedInTime;
    String lastLoggedInAddr;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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
        return "Account{" +
                "username='" + username + '\'' +
                ", status=" + status +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                ", encodedPassword='" + encodedPassword + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", lastLoggedInTime=" + lastLoggedInTime +
                ", lastLoggedInAddr='" + lastLoggedInAddr + '\'' +
                '}';
    }

}