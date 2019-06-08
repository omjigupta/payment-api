package transactions.Service;

import com.google.inject.ImplementedBy;
import transactions.models.Transaction;
import transactions.models.TransactionState;


@ImplementedBy(TransactionStateServiceImpl.class)
public interface TransactionStateService {

    TransactionState stateLog(Transaction transfer, String status);
}
