package io.swagger.model.Entity;

import io.swagger.enums.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private AccountType type;
    private String IBAN;
    private UUID userId;
    private Long absoluteLimit;
    private Long balance;

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
