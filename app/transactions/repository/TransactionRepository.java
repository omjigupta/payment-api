package transactions.repository;

import com.google.inject.ImplementedBy;
import transactions.models.Transaction;

@ImplementedBy(TransactionRepositoryImpl.class)
public interface TransactionRepository {

    void create(Transaction transaction);
}
