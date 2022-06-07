package io.swagger.model.Entity;


import io.swagger.enums.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginEntity{
    @Id
    @GeneratedValue
    private Roles role;
    private String username;
}
