package io.swagger.repository;

import io.swagger.model.Entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ITransactionDTO extends JpaRepository<TransactionEntity, UUID> {
}
