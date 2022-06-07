package io.swagger.model.Responses;


import io.swagger.enums.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginEntity{
    @Id
    @GeneratedValue
    private Roles role;
    private String username;
    private UUID userId;
}
