package io.swagger.model.Entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
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
    private UUID accountFrom;
    private UUID accountTo;
    private UUID user_id;
    private LocalDateTime date;
    private long amount;
}
