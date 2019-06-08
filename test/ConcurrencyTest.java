import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static play.test.Helpers.contentAsString;

public class ConcurrencyTest extends WithApplication {

    private static final String ACCOUNT_1 = "424800";
    private static final String ACCOUNT_2 = "324800";
    private static final String ACCOUNT_3 = "533000";
    private static final String CURRENCY = "USD";

    @Before
    public void assertThatAccountHasBalance() {
        final Http.RequestBuilder accountBalance = AccountTest.getAccountBalance(ACCOUNT_1);
        final Result result = Helpers.route(this.app, accountBalance);
        final JsonNode responseContent = Json.parse(contentAsString(result));
        assertThat(responseContent.get("data").get("balance").asText()).isEqualTo("13240.0");
    }

    @Test
    public void transferMoney_whenConcurrentTransfers_shouldBlockEachTransaction() throws InterruptedException {
        final Http.RequestBuilder transfer1ToAccout1 = TransactionTestHelper.getTransactionRequest("1", ACCOUNT_2, ACCOUNT_1, CURRENCY);
        final Http.RequestBuilder transfer10ToAccout1 = TransactionTestHelper.getTransactionRequest("10", ACCOUNT_3, ACCOUNT_1, CURRENCY);
        final Http.RequestBuilder withdraw40FromAccount1 = TransactionTestHelper.getTransactionRequest("40", ACCOUNT_1, ACCOUNT_3, CURRENCY);

        final CompletableFuture<Result> send1ToAccout1 = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, transfer1ToAccout1));
        final CompletableFuture<Result> send10ToAccout1 = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, transfer10ToAccout1));
        final CompletableFuture<Result> take40FromAccout1 = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, withdraw40FromAccount1));

        Thread.sleep(5000);

        if (send1ToAccout1.isDone() && send10ToAccout1.isDone() && take40FromAccout1.isDone()) {
            final Http.RequestBuilder account1Balance = AccountTest.getAccountBalance(ACCOUNT_1);
            final Result accountBalance = Helpers.route(this.app, account1Balance);
            final JsonNode responseContent = Json.parse(contentAsString(accountBalance));
            assertThat(responseContent.get("data").get("balance").asText()).isEqualTo("13211");
        }
    }
}
