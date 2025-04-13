package com.example.demo.batch.sample.master.user.send;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demo.common.entity.Users;

@Component
public class ExportUsersProcessor implements ItemProcessor<Users, ExportUsersItem> {

    @Override
    public ExportUsersItem process(@SuppressWarnings("null") Users users) throws Exception {
        ExportUsersItem item = new ExportUsersItem();
        item.setId(users.getId());
        item.setName(users.getName());
        item.setDepartment(users.getDepartment());
        item.setCreatedAt(users.getCreatedAt().toString());
        return item;
    }
}
