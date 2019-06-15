package transactions.Service;

import bankaccounts.repository.AccountRepository;
import com.google.inject.Inject;
import global.common.TransactionStatus;
import global.exception.CustomException;
import org.javamoney.moneta.Money;
import transactions.models.Transaction;

public final class TransactionServiceImpl implements TransactionService{

    private AccountRepository accountRepository;

    @Inject
    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction perform(Transaction transaction) {
        transaction.setStatus(TransactionStatus.FAILED);
        validateAccounts(transaction);
        doTransaction(transaction);
        return transaction;
    }

    private void validateAccounts(Transaction transaction) {
        final Long originAccount = transaction.getSenderAccountId();
        final Long destinationAccount = transaction.getReceiverAccountId();

        if (originAccount.compareTo(destinationAccount) == 0) {
            throw new CustomException("Sender and Receiver account can not be same!! Please check it again.");
        }

        if (!accountRepository.checkAccountExists(originAccount)) {
            throw new CustomException("Sender account does not exist");
        }

        if (!accountRepository.checkAccountExists(destinationAccount)) {
            throw new CustomException("Receiver account does not exist");
        }

        if (transaction.getAmount().isNegativeOrZero()) {
            throw new CustomException("Transaction Amount can not be zero or negative.");
        }

    }

    private void doTransaction(Transaction transaction) {
        final Long destination = transaction.getReceiverAccountId();
        final Long origin = transaction.getSenderAccountId();
        final Money amount = transaction.getAmount();
        accountRepository.addAmount(destination, amount);
        accountRepository.removeAmount(origin, amount);
        transaction.setStatus(TransactionStatus.SUCCESS);
    }
}
