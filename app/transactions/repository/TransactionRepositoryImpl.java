package transactions.repository;

import com.google.inject.Inject;
import global.configuration.jooq.JooqClient;
import global.configuration.jooq.records.TransactionRecord;
import transactions.models.Transaction;

import java.math.BigDecimal;

import static global.configuration.jooq.tables.Transaction.TRANSACTION;


public class TransactionRepositoryImpl implements TransactionRepository {


    private JooqClient jooq;

    @Inject
    public TransactionRepositoryImpl(JooqClient jooq) {
        this.jooq = jooq;
    }

    @Override
    public void create(Transaction transaction) {
        final Long senderAccountId = transaction.getSenderAccountId();
        final Long receiverAccountId = transaction.getReceiverAccountId();
        final BigDecimal amount = transaction.getAmount().getNumberStripped();
        final String currency = transaction.getAmount().getCurrency().getCurrencyCode();
        final String transactionId = transaction.getTransferId();

        TransactionRecord transactionRecord = jooq.client().newRecord(TRANSACTION);
        transactionRecord.setSenderAccount(senderAccountId);
        transactionRecord.setReceiverAccount(receiverAccountId);
        transactionRecord.setAmount(amount);
        transactionRecord.setCurrency(currency);
        transactionRecord.setTransactionId(transactionId);

        transactionRecord.store();
    }
}
