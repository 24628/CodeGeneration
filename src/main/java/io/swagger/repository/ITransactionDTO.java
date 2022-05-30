package io.swagger.repository;

import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITransactionDTO extends JpaRepository<TransactionEntity, UUID> {

//    List<TransactionEntity> getAllByAccount_from(UUID acc_from_id);

    List<TransactionEntity> findAllBy();


}
