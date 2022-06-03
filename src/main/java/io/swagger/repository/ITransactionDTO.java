package io.swagger.repository;

import io.swagger.model.Entity.AccountEntity;
import io.swagger.model.Entity.TransactionEntity;
import io.swagger.model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ITransactionDTO extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> getAllByAccountFromAndDate(UUID accountFrom, LocalDateTime date);
    List<TransactionEntity> getAllByAccountFrom(UUID account);

    List<TransactionEntity> findAllBy();

    List<TransactionEntity> findAllByAmountBetweenAndDateBetweenAndAccountFromAndAccountTo(long amount, long amount2, LocalDateTime date, LocalDateTime date2, UUID accountFrom, UUID accountTo);
    List<TransactionEntity> findAllByAmountBetweenAndDateBetweenAndAccountFrom(long amount, long amount2, LocalDateTime date, LocalDateTime date2, UUID accountFrom);
    List<TransactionEntity> findAllByAmountBetweenAndDateBetweenAndAccountTo(long amount, long amount2, LocalDateTime date, LocalDateTime date2, UUID accountTo);
    List<TransactionEntity> findAllByAmountBetweenAndDateBetween(long amount, long amount2, LocalDateTime date, LocalDateTime date2);
}
