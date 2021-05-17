package springboard.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import springboard.example.core.Account;
import springboard.example.core.User;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectByUsername(@Param("username") String username);

    List<User> selectList(@Param("id") Long id, @Param("status") Account.Status status, @Param("username") String username, @Param("name") String name, @Param("createdTime0") Date createdTime0, @Param("createdTime1") Date createdTime1);

}
