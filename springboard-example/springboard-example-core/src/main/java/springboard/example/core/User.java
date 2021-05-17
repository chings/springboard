package springboard.example.core;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value="identity", resultMap="userResultMap")
public class User extends Identity {

    public User() {
        super();
        type = Type.USER;
    }

    Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", account=" + account +
                '}';
    }

}
