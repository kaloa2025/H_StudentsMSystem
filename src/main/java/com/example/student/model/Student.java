package com.example.student.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    private String phone;

    public @Email @NotNull String getEmail() {
        return email;
    }

    public @NotNull String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(@Email @NotNull String email) {
        this.email = email;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getters and Setters
}
