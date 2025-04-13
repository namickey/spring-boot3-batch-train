package com.example.demo.batch.sample.master.user.receive;

import java.time.LocalDate;
import com.example.demo.common.entity.Users;

import lombok.Data;

@Data
public class ImportUsersItem {

    private Integer id;
    private String name;
    private String department;
    private String createdAt;

    /**
     * ユーザエンティティ生成
     * @return
     */
    public Users toUsers() {

        Users users = new Users();
        users.setId(id);
        users.setName(name);
        users.setDepartment(department);
        users.setCreatedAt(LocalDate.parse(createdAt));

        return users;
    }
}
