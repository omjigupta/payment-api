package transactions.request;

import bankaccounts.service.AccountService;
import com.google.inject.Inject;
import global.common.Currency;
import global.exception.CustomException;
import lombok.NonNull;
import org.javamoney.moneta.Money;
import transactions.Service.ExchangeService;
import transactions.models.Transaction;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class TransactionRequestMapper {
    private AccountService accountService;
    private ExchangeService exchangeService;

    @Inject
    public TransactionRequestMapper(AccountService accountService, ExchangeService exchangeService) {
        this.accountService = accountService;
        this.exchangeService = exchangeService;
    }

    public Transaction transform(@NonNull TransactionRequestDTO transactionRequestDTO) {
        final Long sender = parseSenderAccountId(transactionRequestDTO);
        final Long receiver = parseReceiverAccountId(transactionRequestDTO);
        final Money amount = parseTransferAmount(transactionRequestDTO);
        checkSenderBalance(transactionRequestDTO);
        return Transaction.builder()
                .senderAccountId(sender)
                .receiverAccountId(receiver)
                .amount(amount)
                .build();
    }

    private void checkSenderBalance(TransactionRequestDTO transactionRequestDTO) {
        BigDecimal senderBalance = accountService.getAccountBalance(transactionRequestDTO.getSenderAccountId());
        Currency currency = accountService.getAccount(transactionRequestDTO.getSenderAccountId()).getCurrency();
        Money money = exchangeService.exchange(new BigDecimal(transactionRequestDTO.getAmount()), Currency.valueOf(transactionRequestDTO.getCurrency()),currency);
        if (senderBalance.compareTo(money.getNumberStripped()) < 0)
            throw new CustomException("Sender Account does not have sufficient Balance to transfer");
    }

    private Long parseSenderAccountId(@NonNull TransactionRequestDTO transactionRequestDTO) {
        final String senderAccountCandidate = transactionRequestDTO.getSenderAccountId();
        return parseAccountId(senderAccountCandidate);
    }

    private Long parseReceiverAccountId(@NonNull TransactionRequestDTO transactionRequestDTO) {
        final String receiverAccountCandidate = transactionRequestDTO.getReceiverAccountId();
        return parseAccountId(receiverAccountCandidate);
    }

    private Long parseAccountId(@NonNull String accountId) {
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

    private Money parseTransferAmount(@NonNull TransactionRequestDTO transactionRequestDTO) {
        final String amountCandidate = transactionRequestDTO.getAmount();
        final String currencyCandidate = transactionRequestDTO.getCurrency();

        if (isBlank(amountCandidate)) {
            throw new CustomException("amount is needed to proceed with the transfer");
        }

        if (isBlank(currencyCandidate)) {
            throw new CustomException("currency code is needed to proceed with the transfer");
        }

        if (new BigDecimal(amountCandidate).compareTo(BigDecimal.ZERO) > 0) {
            try {
                final BigDecimal amount = new BigDecimal(amountCandidate);
                final CurrencyUnit currency = Monetary.getCurrency(currencyCandidate.toUpperCase());
                if (exchangeService.checkCurrencyCode(currency)) {
                    return Money.of(amount, currency);
                }
                else {
                    throw new CustomException("Currently, We are not supporting input currency transaction!!");
                }
            } catch (NumberFormatException e) {
                throw new CustomException("amount must represent a money value (i.e.: 1030 or 22.65 or 100)");
            } catch (UnknownCurrencyException e) {
                throw new CustomException("currency code specified is not valid (i.e: INR,EUR,USD)");
            }
        } else {
            throw new CustomException("Transaction Amount can not be zero or negative.");
        }

    }
}
