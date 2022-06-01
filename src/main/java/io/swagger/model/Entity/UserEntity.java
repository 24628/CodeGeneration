package io.swagger.model.Entity;

import io.swagger.enums.Roles;


import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private Roles role;
    private String username;



    private String name;

    private String email;
    private String password;
    private Long transactionLimit;

    public UserEntity() {
    }

    public UserEntity(Roles role, String username, String name, String email, String password, Long transactionLimit) {
        this.role = role;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.transactionLimit = transactionLimit;
    }

    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTransaction_limit() {
        return transactionLimit;
    }

    public void setTransaction_limit(Long transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public String getPassword() {
        return password;
    }
}
