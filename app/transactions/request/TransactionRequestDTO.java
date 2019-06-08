package transactions.request;

import lombok.Builder;
import lombok.Data;
import play.mvc.Http;

import static play.libs.Json.fromJson;

/**
 * Transaction request DTO
 *
 * @author omji
 */
@Data
@Builder
public class TransactionRequestDTO {

    private String senderAccountId;
    private String receiverAccountId;
    private String amount;
    private String currency;

    public static TransactionRequestDTO getFromRequest(Http.Request request) {
        return fromJson(request.body().asJson(), TransactionRequestDTO.class);
    }

}