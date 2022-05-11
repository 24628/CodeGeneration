package io.swagger.model.Entity;

import io.swagger.enums.UserType;


import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private UserType type;
    private String name;
    private String email;
    private String password;
    private Long day_limit;
    private Long transaction_limit;

   @ElementCollection(fetch = FetchType.EAGER)
   private
   List<UserType> roles;

  public UserEntity(){}

    public List<UserType> getType() {
        return roles;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Id
    public UUID getUuid() {
        return uuid;
    }

}
