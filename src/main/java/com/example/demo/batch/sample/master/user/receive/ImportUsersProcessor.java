package com.example.demo.batch.sample.master.user.receive;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.demo.common.entity.Users;
import com.example.demo.core.exception.SkipException;

@Component
public class ImportUsersProcessor implements ItemProcessor<ImportUsersItem, Users> {

    @Override
    public Users process(@NonNull ImportUsersItem item) throws Exception {

        // 営業の部門は登録しない
        if ("営業".equals(item.getDepartment())) {
            throw new SkipException("営業は登録できません");
        }
        // ユーザエンティティを生成して返却
        return item.toUsers();
    }
}
