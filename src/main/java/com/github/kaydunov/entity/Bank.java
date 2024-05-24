package com.github.kaydunov.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Bank {
    Long id;
    String name;
    List<User> users;
}
