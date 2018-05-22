package springboard.example.model;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import springboard.lang.ValuedEnum;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {

    public enum Type implements ValuedEnum {
        ROLE(0), USER(1);

        int value;

        Type(int value) {
            this.value = value;
        }

        @Override
        public int getValue() {
            return value;
        }
    }

    Long id;
    Type type;
    String name;
    Date createdTime;

    public Role() {
        type = Type.ROLE;
    }

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
