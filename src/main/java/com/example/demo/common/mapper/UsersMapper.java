package com.example.demo.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.common.entity.Users;

@Mapper
public interface UsersMapper {

    @Insert("insert into users(id, name, department, created_at) values(#{id}, #{name}, #{department}, #{createdAt})")
    void regist(Users users);

    @Insert({
        "<script>",
        "insert into users (id, name, department, created_at) values ",
        "<foreach collection='list' item='user' separator=','>",
        "(#{user.id}, #{user.name}, #{user.department}, #{user.createdAt})",
        "</foreach>",
        "</script>"
    })
    void bulkinsert(List<? extends Users> list);

    @Delete("truncate table users")
    void truncate();

    @Select("select * from users")
    Users selectAll();
}
