package io.swagger.model.Entity;

import io.swagger.enums.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
}
