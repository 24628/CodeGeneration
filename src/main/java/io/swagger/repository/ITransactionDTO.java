package io.swagger.repository;

import io.swagger.helpers.OffsetPageableDate;
import io.swagger.model.Entity.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ITransactionDTO extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> getAllByAccountFromAndDate(UUID accountFrom, LocalDateTime date);
    List<TransactionEntity> getAllByAccountFromOrAccountTo(UUID account,Pageable pg);
    List<TransactionEntity> getAllByAccountFromOrAccountTo(UUID account);

    List<TransactionEntity> findAllBy(Pageable pg);

    List<TransactionEntity> findAllByAmountBetweenAndDateBetweenAndAccountFromAndAccountTo(long amount, long amount2, LocalDateTime date, LocalDateTime date2, UUID accountFrom, UUID accountTo, OffsetPageableDate offsetPageableDate);
    List<TransactionEntity> findAllByAmountBetweenAndDateBetweenAndAccountFrom(long amount, long amount2, LocalDateTime date, LocalDateTime date2, UUID accountFrom, OffsetPageableDate offsetPageableDate);
    List<TransactionEntity> findAllByAmountBetweenAndDateBetweenAndAccountTo(long amount, long amount2, LocalDateTime date, LocalDateTime date2, UUID accountTo, OffsetPageableDate offsetPageableDate);
    List<TransactionEntity> findAllByAmountBetweenAndDateBetween(long amount, long amount2, LocalDateTime date, LocalDateTime date2, OffsetPageableDate offsetPageableDate);
}
