import bankaccounts.repository.AccountRepositoryImpl;
import com.fasterxml.jackson.databind.JsonNode;
import global.common.Currency;
import global.common.TransactionStatus;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import transactions.Service.ExchangeServiceImpl;
import transactions.Service.TransactionServiceImpl;
import transactions.models.Transaction;
import transactions.request.TransactionRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static play.test.Helpers.contentAsString;

public class TransactionBetweenAccountsTest extends WithApplication {
    private static final String AMOUNT = "200.50";
    private static final String SENDER_ACC = "533000";
    private static final String RECEIVER_ACC = "324800";
    private static final String CURRENCY = "INR";

    @Mock
    private AccountRepositoryImpl accountRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void perform() throws InterruptedException {

        when(accountRepository.checkAccountExists(Long.decode(RECEIVER_ACC))).thenReturn(true);
        when(accountRepository.checkAccountExists(Long.decode(SENDER_ACC))).thenReturn(true);

        final Http.RequestBuilder build = TransactionTestHelper.getTransactionRequest(AMOUNT, SENDER_ACC, RECEIVER_ACC, CURRENCY);
        final CompletableFuture<Result> sendToAccount = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, build));

        Thread.sleep(1000);

        if (sendToAccount.isDone()) {
            final Http.RequestBuilder account1Balance = AccountTest.getAccountBalance(SENDER_ACC);
            final Result accountBalance = Helpers.route(this.app, account1Balance);
            final JsonNode responseContent = Json.parse(contentAsString(accountBalance));
            assertThat(responseContent.get("data").get("balance").asText()).isEqualTo("4800.0");
        }
    }
}
