package springboard.example.bean;

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

    public Account setUsername(String username) {
        this.username = username;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Account setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Account setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public Account setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
        return this;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Account setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public Account setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public Date getLastLoggedInTime() {
        return lastLoggedInTime;
    }

    public Account setLastLoggedInTime(Date lastLoggedInTime) {
        this.lastLoggedInTime = lastLoggedInTime;
        return this;
    }

    public String getLastLoggedInAddr() {
        return lastLoggedInAddr;
    }

    public Account setLastLoggedInAddr(String lastLoggedInAddr) {
        this.lastLoggedInAddr = lastLoggedInAddr;
        return this;
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