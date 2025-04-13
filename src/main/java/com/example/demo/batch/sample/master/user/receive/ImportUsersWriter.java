package com.example.demo.batch.sample.master.user.receive;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.demo.common.entity.Users;
import com.example.demo.common.mapper.UsersMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImportUsersWriter implements ItemWriter<Users> {
    
    private final UsersMapper usersMapper;

    @Override
    public void write(@NonNull Chunk<? extends Users> list) throws Exception {

        usersMapper.bulkinsert(list.getItems());

        // for (Users users: list) {
        //     // DBに登録する
        //     usersMapper.regist(users);
        // }
    }
}
