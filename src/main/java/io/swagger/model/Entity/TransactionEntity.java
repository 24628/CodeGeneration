package io.swagger.model.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String accountFrom;
    private String accountTo;
    private UUID user_id;
    private LocalDateTime date;
    private long amount;
}
