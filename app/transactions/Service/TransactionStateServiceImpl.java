package transactions.Service;

import bankaccounts.models.Account;
import bankaccounts.repository.AccountRepository;
import com.google.inject.Inject;
import global.common.TransactionStatus;
import transactions.models.Transaction;
import transactions.models.TransactionState;
import transactions.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionStateServiceImpl implements TransactionStateService {

    private TransactionRepository transferRepository;
    private AccountRepository accountRepository;

    @Inject
    public TransactionStateServiceImpl(TransactionRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public TransactionState stateLog(Transaction transaction, String status) {
        persist(transaction);
        final Long originAccountId = transaction.getSenderAccountId();
        final Long destinationAccountId = transaction.getReceiverAccountId();
        final Account sender = accountRepository.findAccount(originAccountId);
        final Account receiver = accountRepository.findAccount(destinationAccountId);
        final BigDecimal amount = transaction.getAmount().getNumberStripped();
        final String currencyCode = transaction.getAmount().getCurrency().getCurrencyCode();

        return TransactionState.builder()
                .status(TransactionStatus.valueOf(status))
                .senderAccount(sender)
                .receiverAccount(receiver)
                .amount(amount)
                .currency(currencyCode)
                .build();
    }

    private void persist(Transaction transaction) {
        transaction.setTransferId(UUID.randomUUID().toString());
        transferRepository.create(transaction);
    }
}
