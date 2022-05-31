package io.swagger.model.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class DayLimitEntity {

    @Id
    @GeneratedValue
    private UUID uuid;

    private UUID userId;

    private Long actualLimit;

    private Long current;

    public DayLimitEntity() {}

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getActualLimit() {
        return actualLimit;
    }

    public void setActualLimit(Long actualLimit) {
        this.actualLimit = actualLimit;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }
}
