package transactions.models;

import bankaccounts.models.Account;
import com.fasterxml.jackson.databind.JsonNode;
import global.common.TransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static play.libs.Json.toJson;

@Getter
@Setter
@Builder
public final class TransactionState {

    private TransactionStatus status;
    private Account senderAccount;
    private Account receiverAccount;
    private BigDecimal amount;
    private String currency;

    public JsonNode asJson() {
        return toJson(this);
    }
}
