package io.swagger.model.Entity;

import io.swagger.enums.AccountType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class AccountEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private AccountType type;
    private String IBAN;
    private UUID user_uuid;
    private Long absolute_limit;

    public AccountEntity() {
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public UUID getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(UUID user_uuid) {
        this.user_uuid = user_uuid;
    }

    public Long getAbsolute_limit() {
        return absolute_limit;
    }

    public void setAbsolute_limit(Long absolute_limit) {
        this.absolute_limit = absolute_limit;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Id
    public UUID getUuid() {
        return uuid;
    }
}
