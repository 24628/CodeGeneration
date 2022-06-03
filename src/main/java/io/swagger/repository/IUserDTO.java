package io.swagger.repository;

import io.swagger.enums.Roles;
import io.swagger.model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUserDTO extends JpaRepository<UserEntity, UUID> {

    UserEntity findByUsername(String username);

    UserEntity findUserEntityByUuid(String uuid);

    UserEntity findUserEntitiesByPinCode(Integer pinCode);

    UserEntity findUserEntityByRoleIs(Roles role);

    List<UserEntity> findAllByRoleIs(Roles role);
}
