package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;
import java.util.List;
import java.util.Locale;

public class FinanceUseCase {
    private final ITransactionRepository repository;

    public FinanceUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction addTransaction(String description, long amount, TransactionType type) {
        return repository.save(description, amount, type);
    }

    public boolean removeTransaction(int id) {
        return repository.deleteById(id);
    }

    public List<Transaction> searchTransactions(String keyword) {
        String lower = keyword.toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(t -> t.getDescription().toLowerCase(Locale.ROOT).contains(lower))
                .toList();
    }

    public List<Transaction> sortTransactions(SortOption option) {
        return repository.findAll().stream().sorted(option.comparator()).toList();
    }

    /** @return total pemasukan dikurangi total pengeluaran */
    public long getBalance() {
        long balance = 0;
        for (Transaction transaction : repository.findAll()) {
            balance += transaction.getType() == TransactionType.INCOME
                    ? transaction.getAmount()
                    : -transaction.getAmount();
        }
        return balance;
    }
}
