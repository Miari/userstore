package com.boroday.userstore.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class User {
    private long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Double salary;
    private LocalDate dateOfBirth;
}
