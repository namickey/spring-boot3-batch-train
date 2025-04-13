package com.example.demo.batch.sample.master.user.send;

import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.stereotype.Component;

@Component
public class ExportUsersFieldExtractor implements FieldExtractor<ExportUsersItem> {

    @SuppressWarnings("null")
    @Override
    public Object[] extract(ExportUsersItem item) {
        return new Object[] {
                item.getId().toString(),
                item.getName(),
                item.getDepartment(),
                item.getCreatedAt()
        };
    }
    
}
