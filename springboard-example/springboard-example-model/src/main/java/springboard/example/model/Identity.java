package springboard.example.model;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value="identity", resultMap="identityResultMap")
public class Identity implements Serializable {

    public enum Type implements IEnum<Integer>, Serializable {
        ROLE(0), USER(1);

        int value;

        Type(int value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() { return String.valueOf(value); }

    }

    @TableId(type = IdType.AUTO)
    Long id;
    Type type = Type.ROLE;
    String name;
    Date createdTime;
    Date updatedTime;

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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "Identity{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }

}
