package transactions.Service;

import com.google.inject.ImplementedBy;
import transactions.models.Transaction;


@ImplementedBy(TransactionServiceImpl.class)
public interface TransactionService {
    Transaction perform(Transaction transaction);
}
