package bankaccounts.service;

import bankaccounts.repository.AccountRepository;
import com.google.inject.Inject;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        return accountRepository.findBalance(Long.decode(accountNumber));
    }
}
