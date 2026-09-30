package domain.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import java.util.List;

public interface ITransactionRepository {
    List<Transaction> findAll();

    Transaction save(String description, long amount, TransactionType type);

    boolean deleteById(int id);
}
