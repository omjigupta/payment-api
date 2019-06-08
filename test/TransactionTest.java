import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import transactions.request.TransactionRequestDTO;

import static org.assertj.core.api.Assertions.assertThat;

import static play.libs.Json.toJson;
import static play.mvc.Http.HttpVerbs.POST;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.CREATED;
import static play.test.Helpers.contentAsString;

public class TransactionTest extends WithApplication {

    private static final String ENDPOINT = "/v1/transactions";

    private static final String AMOUNT = "2000.60";
    private static final String SENDER_ACCOUNT = "533000";
    private static final String RECEIVER_ACCOUNT = "324800";
    private static final String CURRENCY = "INR";

    @Test
    public void transferMoney_whenDataIsValid_ShouldReturnCreated() {
        final Result transfer = Helpers.route(this.app,
                new Http.RequestBuilder()
                        .method(POST)
                        .uri(ENDPOINT)
                        .bodyJson(toJson(TransactionRequestDTO.builder()
                                .amount(AMOUNT)
                                .senderAccountId(SENDER_ACCOUNT)
                                .receiverAccountId(RECEIVER_ACCOUNT)
                                .currency(CURRENCY)
                                .build())));

        final JsonNode responseContent = Json.parse(contentAsString(transfer));

        assertThat(transfer.status()).isEqualTo(CREATED);
        responseContent.has("status");
        assertThat(responseContent.get("status").textValue()).isEqualTo("SUCCESS");
    }

    @Test
    public void transaction_whenCurrencyIsInvalid_shouldReturnBadRequest() {
        final Result performTransfer = Helpers.route(this.app,
                new Http.RequestBuilder()
                        .method(POST)
                        .uri(ENDPOINT)
                        .bodyJson(toJson(TransactionRequestDTO.builder()
                                .amount(AMOUNT)
                                .senderAccountId(SENDER_ACCOUNT)
                                .receiverAccountId(RECEIVER_ACCOUNT)
                                .currency("YEN")
                                .build())));

        assertThat(performTransfer.status()).isEqualTo(BAD_REQUEST);
    }

}
