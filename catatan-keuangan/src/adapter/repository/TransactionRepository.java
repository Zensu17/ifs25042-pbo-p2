package adapter.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements ITransactionRepository {
    private final List<Transaction> data = new ArrayList<>();
    private int nextId = 1;

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Transaction save(String description, long amount, TransactionType type) {
        Transaction transaction = new Transaction(nextId++, description, amount, type);
        data.add(transaction);
        return transaction;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(t -> t.getId() == id);
    }
}
