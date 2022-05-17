package io.swagger.repository;

import io.swagger.model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByUsername(String username);
}
