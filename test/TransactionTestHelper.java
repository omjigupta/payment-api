import org.junit.Test;
import play.mvc.Http;
import transactions.request.TransactionRequestDTO;

import static play.libs.Json.toJson;
import static play.mvc.Http.HttpVerbs.POST;

public class TransactionTestHelper {

    private static final String ENDPOINT = "/v1/transactions";

    @Test
    public void test() {
        assert true;
    }

    public static Http.RequestBuilder getTransactionRequest(String amount, String sender, String receiver, String currency) {
        return new Http.RequestBuilder()
                .method(POST)
                .uri(ENDPOINT)
                .bodyJson(toJson(TransactionRequestDTO.builder()
                        .amount(amount)
                        .senderAccountId(sender)
                        .receiverAccountId(receiver)
                        .currency(currency)
                        .build()));
    }
}
