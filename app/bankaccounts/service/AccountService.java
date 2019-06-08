package bankaccounts.service;

import com.google.inject.ImplementedBy;

import java.math.BigDecimal;

@ImplementedBy(AccountServiceImpl.class)
public interface AccountService {

    BigDecimal getAccountBalance(String accountNumber);
}
