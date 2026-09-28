package adapter.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionRepository implements ITransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();
    private int nextId = 1;

    @Override
    public Transaction save(String description, long amount, TransactionType type) {
        Transaction transaction = new Transaction(nextId++, description, amount, type);
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    @Override
    public Optional<Transaction> findById(int id) {
        return transactions.stream()
                .filter(transaction -> transaction.getId() == id)
                .findFirst();
    }

    @Override
    public boolean deleteById(int id) {
        return transactions.removeIf(transaction -> transaction.getId() == id);
    }
}
