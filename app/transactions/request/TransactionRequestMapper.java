package transactions.request;

import global.exception.CustomException;
import org.javamoney.moneta.Money;
import transactions.models.Transaction;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class TransactionRequestMapper {

    public Transaction transform(TransactionRequestDTO transactionRequestDTO) {
        final Long sender = parseSenderAccountId(transactionRequestDTO);
        final Long receiver = parseReceiverAccountId(transactionRequestDTO);
        final Money amount = parseTransferAmount(transactionRequestDTO);

        return Transaction.builder()
                .senderAccountId(sender)
                .receiverAccountId(receiver)
                .amount(amount)
                .build();
    }

    private Long parseSenderAccountId(TransactionRequestDTO transactionRequestDTO) {
        final String senderAccountCandidate = transactionRequestDTO.getSenderAccountId();
        return parseAccountId(senderAccountCandidate);
    }

    private Long parseReceiverAccountId(TransactionRequestDTO transactionRequestDTO) {
        final String receiverAccountCandidate = transactionRequestDTO.getReceiverAccountId();
        return parseAccountId(receiverAccountCandidate);
    }

    private Long parseAccountId(String accountId) {
        if (isBlank(accountId)) {
            throw new CustomException("origin and destination accounts are needed for the transfer");
        }

        if (accountId.length() > 9) {
            throw new CustomException("account numbers must not contain more than 9 characters");
        }

        try {
            return new Long(accountId);
        } catch (NumberFormatException e) {
            throw new CustomException("accounts must contain numbers only");
        }
    }

    private Money parseTransferAmount(TransactionRequestDTO transactionRequestDTO) {
        final String amountCandidate = transactionRequestDTO.getAmount();
        final String currencyCandidate = transactionRequestDTO.getCurrency();

        if (isBlank(amountCandidate)) {
            throw new CustomException("amount is needed to proceed with the transfer");
        }

        if (isBlank(currencyCandidate)) {
            throw new CustomException("currency code is needed to proceed with the transfer");
        }

        try {
            final BigDecimal amount = new BigDecimal(amountCandidate);
            final CurrencyUnit currency = Monetary.getCurrency(currencyCandidate.toUpperCase());
            return Money.of(amount, currency);
        } catch (NumberFormatException e) {
            throw new CustomException("amount must represent a money value (i.e.: 1030 or 22.65 or 100)");
        } catch (UnknownCurrencyException e) {
            throw new CustomException("currency code specified is not valid (i.e: INR,EUR,USD)");
        }
    }
}
