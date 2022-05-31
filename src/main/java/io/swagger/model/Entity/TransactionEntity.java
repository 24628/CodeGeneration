package io.swagger.model.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue
    private UUID uuid;
    private UUID accountFrom;
    private UUID accountTo;
    private UUID user_id;
    private Date date;
    private long amount;

    public TransactionEntity(){

    }

    public UUID getAccount_from() {
        return accountFrom;
    }

    public void setAccount_from(UUID accountFrom) {
        this.accountFrom = accountFrom;
    }

    public UUID getAccount_to() {
        return accountTo;
    }

    public void setAccount_to(UUID accountTo) {
        this.accountTo = accountTo;
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

    public UUID getUuid() {
        return uuid;
    }
}
