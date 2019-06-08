import com.fasterxml.jackson.databind.JsonNode;
import global.common.Currency;
import global.common.TransactionStatus;
import org.javamoney.moneta.Money;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import transactions.models.Transaction;
import static org.assertj.core.api.Assertions.assertThat;

import static play.libs.Json.toJson;
import static play.mvc.Http.HttpVerbs.POST;
import static play.mvc.Http.Status.CREATED;
import static play.test.Helpers.contentAsString;

public class TransactionTest extends WithApplication {

    @Test
    public void transferMoney_whenDataIsValid_ShouldReturnCreated() {
        Money moneyof = Money.of(12, Currency.USD.toString());
        final Result transfer = Helpers.route(this.app,
                new Http.RequestBuilder()
                        .method(POST)
                        .uri("/v1/transactions")
                        .bodyJson(toJson(Transaction.builder()
                                .transferId("test101")
                                .amount(moneyof)
                                .senderAccountId(1111l)
                                .receiverAccountId(2222l)
                                .build())));

        final JsonNode responseContent = Json.parse(contentAsString(transfer));

        assertThat(transfer.status()).isEqualTo(CREATED);
        responseContent.has("status");
        assertThat(responseContent.get("status").textValue()).isEqualTo(TransactionStatus.valueOf("SUCCESS"));
    }

}
