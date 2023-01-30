package com.barfix.back.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "user", schema = "public")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @NonNull
    public String id;

    @Column(name = "first_name")
    @NonNull
    public String firstName;

    @Column(name = "last_name")
    @NonNull
    public String lastName;

    @Column(name = "email")
    @NonNull
    public String email;

    @Column(name = "password")
    @NonNull
    public String password;

    @Column(name = "role")
    @NonNull
    public String role;
    @Column(name = "birthdate")
    @NonNull
    public Date birthdate;

}
