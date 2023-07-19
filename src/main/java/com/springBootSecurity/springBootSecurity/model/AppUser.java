package com.springBootSecurity.springBootSecurity.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AppUser ")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public int id;
    public  String username;
    public  String email;
    public  String password;
    public  String  roles;
}
