package transactions.models;

import global.common.TransactionStatus;
import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {

    private String transferId;
    private Long senderAccountId;
    private Long receiverAccountId;
    private Money amount;
    private TransactionStatus status;
    private LocalDateTime timestamp;

}
