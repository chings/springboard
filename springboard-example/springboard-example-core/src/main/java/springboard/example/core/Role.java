package springboard.example.core;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value="identity", resultMap="roleResultMap")
public class Role extends Identity {

    public Role() {
        super();
        type = Type.ROLE;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }

}
