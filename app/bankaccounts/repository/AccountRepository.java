package bankaccounts.repository;

import bankaccounts.models.Account;
import com.google.inject.ImplementedBy;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;

/**
 * Account repository interface for account model
 *
 * @author omji
 */
@ImplementedBy(AccountRepositoryImpl.class)
public interface AccountRepository {

    BigDecimal findBalance(Long accountNumber);

    CurrencyUnit findCurrency(Long accountNumber);

    Long findAccountNumber(String customerGovtId);

    boolean checkAccountExists(Long accountNumber);

    Account findAccount(Long accountNumber);

    boolean addAmount(Long accountNumber, Money amount);

    boolean removeAmount(Long accountNumber, Money amount);
}
