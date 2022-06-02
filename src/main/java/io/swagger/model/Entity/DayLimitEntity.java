package io.swagger.model.Entity;

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
public class DayLimitEntity {

    @Id
    @GeneratedValue
    private UUID uuid;
    private UUID userId;
    private Long actualLimit;
    private Long current;

}
