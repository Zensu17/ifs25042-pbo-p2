package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class FinanceUseCase {
    private final ITransactionRepository repository;

    public FinanceUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction add(String description, long amount, TransactionType type) {
        return repository.save(description, amount, type);
    }

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    public List<Transaction> search(String keyword) {
        String needle = keyword.toLowerCase(Locale.ROOT);
        List<Transaction> found = new ArrayList<>();
        for (Transaction transaction : repository.findAll()) {
            if (transaction.getDescription().toLowerCase(Locale.ROOT).contains(needle)) {
                found.add(transaction);
            }
        }
        return found;
    }

    public List<Transaction> sort(SortOption option) {
        List<Transaction> sorted = repository.findAll();
        sorted.sort(comparatorFor(option));
        return sorted;
    }

    public boolean delete(int id) {
        return repository.deleteById(id);
    }

    public long getBalance() {
        long income = 0;
        long expense = 0;
        for (Transaction transaction : repository.findAll()) {
            if (transaction.getType() == TransactionType.INCOME) {
                income += transaction.getAmount();
            } else {
                expense += transaction.getAmount();
            }
        }
        return income - expense;
    }

    private Comparator<Transaction> comparatorFor(SortOption option) {
        Comparator<Transaction> byId = Comparator.comparingInt(Transaction::getId);
        switch (option) {
            case AMOUNT_ASC:
                return Comparator.comparingLong(Transaction::getAmount).thenComparing(byId);
            case AMOUNT_DESC:
                return Comparator.comparingLong(Transaction::getAmount).reversed().thenComparing(byId);
            case INCOME_FIRST:
                return Comparator.comparingInt((Transaction t) -> t.getType() == TransactionType.INCOME ? 0 : 1)
                        .thenComparing(byId);
            case EXPENSE_FIRST:
                return Comparator.comparingInt((Transaction t) -> t.getType() == TransactionType.EXPENSE ? 0 : 1)
                        .thenComparing(byId);
            default:
                return byId;
        }
    }
}
