package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String email;
    private String pwd;
    private String name;
    private String details;

    private boolean isActive;

    private Office office;
    private Set<Role> roles;

    private Timestamp createdTs;
    private Timestamp updatedTs;

    public User(String email, String pwd, String name, String details) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.details = details;
    }
}
