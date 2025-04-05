package com.example.demo.common.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Users {
    private Integer id;
    private String name;
    private String department;
    private LocalDate createdAt;
}
