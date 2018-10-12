package springboard.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;
import java.util.Date;

@TableName("roles")
public class Role implements Serializable {

    public enum Type implements IEnum, Serializable {
        ROLE(0), USER(1);

        int value;

        Type(int value) {
            this.value = value;
        }

        @Override
        public Serializable getValue() {
            return value;
        }
    }

    @TableId(type = IdType.AUTO)
    Long id;
    Type type = Type.ROLE;
    String name;
    Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

}
