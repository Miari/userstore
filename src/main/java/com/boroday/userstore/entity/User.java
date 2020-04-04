package com.boroday.userstore.entity;

import java.time.LocalDate;

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private Double salary;
    private LocalDate dateOfBirth;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String toString() {
        return "UserID = " + id + "; FirstName = " + firstName + "; LastName = " + lastName + "; Salary = " + salary + "; DateOfBirth = " + dateOfBirth + ".";
    }

    public boolean equals(Object user) {
        if (this == user) {
            return true;
        }
        if (user == null || getClass() != user.getClass()) {
            return false;
        }
        User newUser = (User) user;
        return id == newUser.id &&
                firstName.equals(newUser.firstName) &&
                lastName.equals(newUser.lastName) &&
                salary.equals(newUser.salary) &&
                dateOfBirth.equals(newUser.dateOfBirth);
    }
}
