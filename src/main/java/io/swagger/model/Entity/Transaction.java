package io.swagger.model.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private UUID uuid;
    private UUID account_from;
    private UUID account_to;
    private UUID user_id;
    private Date date;
    private long amount;

    public Transaction(){

    }

    public UUID getAccount_from() {
        return account_from;
    }

    public void setAccount_from(UUID account_from) {
        this.account_from = account_from;
    }

    public UUID getAccount_to() {
        return account_to;
    }

    public void setAccount_to(UUID account_to) {
        this.account_to = account_to;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Id
    public UUID getUuid() {
        return uuid;
    }
}
