package bankaccounts.service;

import bankaccounts.models.Account;
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

    @Override
    public Account getAccount(String accountNumber) {
        return accountRepository.findAccount(Long.decode(accountNumber));
    }

    @Override
    public boolean checkAccountExists(String accountNumber) {
        return accountRepository.checkAccountExists(Long.decode(accountNumber));
    }
}
