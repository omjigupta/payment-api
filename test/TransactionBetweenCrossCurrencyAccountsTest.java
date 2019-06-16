import bankaccounts.repository.AccountRepositoryImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.test.Helpers.contentAsString;

public class TransactionBetweenCrossCurrencyAccountsTest extends WithApplication {
    private static final String AMOUNT = "2";
    private static final String SENDER_ACC = "124800";
    private static final String RECEIVER_ACC = "533000";
    private static final String CURRENCY = "EUR";

    @Mock
    private AccountRepositoryImpl accountRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransactionDifferentCurrency() throws InterruptedException {

        when(accountRepository.checkAccountExists(Long.decode(RECEIVER_ACC))).thenReturn(true);
        when(accountRepository.checkAccountExists(Long.decode(SENDER_ACC))).thenReturn(true);

        final Http.RequestBuilder build = TransactionTestHelper.getTransactionRequest(AMOUNT, SENDER_ACC, RECEIVER_ACC, CURRENCY);
        final CompletableFuture<Result> sendToAccount = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, build));

        Thread.sleep(1000);

        if (sendToAccount.isDone()) {
            final Http.RequestBuilder account1Balance = AccountTest.getAccountBalance(RECEIVER_ACC);
            final Result accountBalance = Helpers.route(this.app, account1Balance);
            final JsonNode responseContent = Json.parse(contentAsString(accountBalance));
            assertThat(responseContent.get("data").get("balance").asText()).isEqualTo("5157.2");
        }
    }

    @Test
    public void testTransactionNotEnoughFund() throws InterruptedException {
        String AMOUNT = "11"; //Account balance is 10 EURO in sender account

        when(accountRepository.checkAccountExists(Long.decode(RECEIVER_ACC))).thenReturn(true);
        when(accountRepository.checkAccountExists(Long.decode(SENDER_ACC))).thenReturn(true);

        final Http.RequestBuilder build = TransactionTestHelper.getTransactionRequest(AMOUNT, SENDER_ACC, RECEIVER_ACC, CURRENCY);
        final CompletableFuture<Result> sendToAccount = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, build));

        Thread.sleep(1000);

        if (sendToAccount.isCompletedExceptionally()) {
            assertThat(sendToAccount.isCompletedExceptionally()).isEqualTo(BAD_REQUEST);
        }
    }


    @Test
    public void testTransactionWithNegativeAmount() throws InterruptedException {
        String AMOUNT = "-1"; //Account balance is 10 EURO in sender account

        when(accountRepository.checkAccountExists(Long.decode(RECEIVER_ACC))).thenReturn(true);
        when(accountRepository.checkAccountExists(Long.decode(SENDER_ACC))).thenReturn(true);

        final Http.RequestBuilder build = TransactionTestHelper.getTransactionRequest(AMOUNT, SENDER_ACC, RECEIVER_ACC, CURRENCY);
        final CompletableFuture<Result> sendToAccount = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, build));

        Thread.sleep(1000);

        if (sendToAccount.isCompletedExceptionally()) {
            assertThat(sendToAccount.isCompletedExceptionally()).isEqualTo(BAD_REQUEST);
        }
    }

    @Test
    public void testTransactionWithNullAmount() throws InterruptedException {
        String AMOUNT = ""; //Account balance is 10 EURO in sender account

        when(accountRepository.checkAccountExists(Long.decode(RECEIVER_ACC))).thenReturn(true);
        when(accountRepository.checkAccountExists(Long.decode(SENDER_ACC))).thenReturn(true);

        final Http.RequestBuilder build = TransactionTestHelper.getTransactionRequest(AMOUNT, SENDER_ACC, RECEIVER_ACC, CURRENCY);
        final CompletableFuture<Result> sendToAccount = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, build));

        Thread.sleep(1000);

        if (sendToAccount.isCompletedExceptionally()) {
            assertThat(sendToAccount.isCompletedExceptionally()).isEqualTo(BAD_REQUEST);
        }
    }

    @Test
    public void testTransactionWithCurrencyNonExistence() throws InterruptedException {
        String CURRENCY = "JPY";

        when(accountRepository.checkAccountExists(Long.decode(RECEIVER_ACC))).thenReturn(true);
        when(accountRepository.checkAccountExists(Long.decode(SENDER_ACC))).thenReturn(true);

        final Http.RequestBuilder build = TransactionTestHelper.getTransactionRequest(AMOUNT, SENDER_ACC, RECEIVER_ACC, CURRENCY);
        final CompletableFuture<Result> sendToAccount = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, build));

        Thread.sleep(1000);

        if (sendToAccount.isCompletedExceptionally()) {
            assertThat(sendToAccount.isCompletedExceptionally()).isEqualTo(BAD_REQUEST);
        }
    }

    @Test
    public void testTransactionWhenSenderAccountNonAvailable() throws InterruptedException {
        String SENDER_ACC = "12480";

        when(accountRepository.checkAccountExists(Long.decode(RECEIVER_ACC))).thenReturn(false);
        when(accountRepository.checkAccountExists(Long.decode(SENDER_ACC))).thenReturn(true);

        final Http.RequestBuilder build = TransactionTestHelper.getTransactionRequest(AMOUNT, SENDER_ACC, RECEIVER_ACC, CURRENCY);
        final CompletableFuture<Result> sendToAccount = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, build));

        Thread.sleep(1000);

        if (sendToAccount.isCompletedExceptionally()) {
            assertThat(sendToAccount.isCompletedExceptionally()).isEqualTo(BAD_REQUEST);
        }
    }
}
