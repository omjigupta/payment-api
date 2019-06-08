package bankaccounts.repository;

import bankaccounts.models.Account;
import com.google.inject.Inject;
import customers.repository.CustomerRepository;
import global.configuration.jooq.JooqClient;
import org.javamoney.moneta.Money;
import org.jooq.Record1;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;

import static global.configuration.jooq.tables.Public.ACCOUNT;


public class AccountRepositoryImpl implements AccountRepository {

    private JooqClient jooq;
    private CustomerRepository customerRepository;

    @Inject
    public AccountRepositoryImpl(JooqClient jooq, CustomerRepository customerRepository) {
        this.jooq = jooq;
        this.customerRepository = customerRepository;
    }



    @Override
    public BigDecimal findBalance(Long accountNumber) {
        final Record1<BigDecimal> balance = jooq.client()
                .select(ACCOUNT.BALANCE)
                .from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.equal(accountNumber))
                .fetchOne();

        return balance.value1();
    }

    @Override
    public CurrencyUnit findCurrency(Long accountNumber) {
        final Record1<String> currency = jooq.client()
                .select(ACCOUNT.CURRENCY)
                .from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.equal(accountNumber))
                .fetchOne();

        return Monetary.getCurrency(currency.value1());
    }

    @Override
    public Long findAccountNumber(String customerGovtId) {
        final Long customerId = customerRepository.findId(customerGovtId);

        final Record1<Long> accoutNumber = jooq.client()
                .select(ACCOUNT.ACCOUNT_NUMBER)
                .from(ACCOUNT)
                .where(ACCOUNT.CUSTOMER_ID.equal(customerId))
                .fetchOne();

        return accoutNumber.value1();
    }

    @Override
    public boolean checkAccountExists(Long accountNumber) {
        return jooq.client()
                .select(ACCOUNT.ID)
                .from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.equal(accountNumber))
                .fetchOptional()
                .isPresent();
    }

    @Override
    public Account findAccount(Long accountNumber) {
        return jooq.client()
                .select()
                .from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.equal(accountNumber))
                .fetchOne().into(Account.class);
    }

    @Override
    public boolean addAmount(Long accountNumber, Money amount) {
        final int amountAdded = jooq.client()
                .update(ACCOUNT)
                .set(ACCOUNT.BALANCE, ACCOUNT.BALANCE.add(amount.getNumberStripped()))
                .where(ACCOUNT.ACCOUNT_NUMBER.equal(accountNumber))
                .execute();

        return amountAdded > 0;
    }

    @Override
    public boolean removeAmount(Long accountNumber, Money amount) {
        final int amountRemoved = jooq.client()
                .update(ACCOUNT)
                .set(ACCOUNT.BALANCE, ACCOUNT.BALANCE.sub(amount.getNumberStripped()))
                .where(ACCOUNT.ACCOUNT_NUMBER.equal(accountNumber))
                .execute();

        return amountRemoved > 0;
    }
}
