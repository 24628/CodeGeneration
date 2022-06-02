package io.swagger.model.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue
    private UUID uuid;
    private UUID accountFrom;
    private UUID accountTo;
    private UUID user_id;
    private Date date;
    private long amount;
}
