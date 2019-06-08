package transactions.repository;

import transactions.models.Transaction;

public interface TransactionRepository {

    void create(Transaction transaction);
}
