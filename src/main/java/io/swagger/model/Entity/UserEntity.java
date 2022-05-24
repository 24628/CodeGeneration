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
    private String email;
    private String password;
    private Long day_limit;
    private Long transaction_limit;

    public UserEntity() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDay_limit() {
        return day_limit;
    }

    public void setDay_limit(Long day_limit) {
        this.day_limit = day_limit;
    }

    public Long getTransaction_limit() {
        return transaction_limit;
    }

    public void setTransaction_limit(Long transaction_limit) {
        this.transaction_limit = transaction_limit;
    }
}
