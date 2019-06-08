package bankaccounts.controller;

import bankaccounts.repository.AccountRepository;
import bankaccounts.service.AccountService;
import com.google.inject.Inject;
import global.common.BaseController;
import lombok.extern.slf4j.Slf4j;
import play.mvc.Result;

import javax.inject.Singleton;
import java.util.HashMap;

import static play.libs.Json.toJson;

@Slf4j
@Singleton
public class AccountController extends BaseController {

    private AccountService accountService;
    private AccountRepository accountRepository;

    @Inject
    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    public Result checkBalance(String accountNumber) {
        if (accountRepository.checkAccountExists(Long.decode(accountNumber))) {
            final HashMap<Object, Object> wrapper = new HashMap<>();
            wrapper.put("balance", accountService.getAccountBalance(accountNumber));
            return success(toJson(wrapper));
        } else {
            return failure(404, "Account number does not  exist!!!");
        }

    }
}

