package bankaccounts.service;

import bankaccounts.models.Account;
import com.google.inject.ImplementedBy;

import java.math.BigDecimal;

@ImplementedBy(AccountServiceImpl.class)
public interface AccountService {

    BigDecimal getAccountBalance(String accountNumber);

    Account getAccount(String accountNumber);

    boolean checkAccountExists(String accountNumber);
}
