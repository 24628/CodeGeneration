package io.swagger.repository;

import io.swagger.enums.AccountType;
import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAccountDTO extends JpaRepository<AccountEntity, UUID> {

     AccountEntity getAccountByIBAN(String IBAN);

     List<AccountEntity> getAllByUuidIs(UUID userid);

     List<AccountEntity> getAllByTypeIsNot(AccountType type, Pageable pg);

     AccountEntity getAccountEntityByUserIdAndTypeIsNot(UUID userId, AccountType type);

     AccountEntity findByTypeIs(AccountType type);

     Boolean findByUserIdIs(UUID userId);
}
