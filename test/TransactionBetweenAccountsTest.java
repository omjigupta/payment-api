import bankaccounts.repository.AccountRepositoryImpl;
import global.common.TransactionStatus;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import play.test.WithApplication;
import transactions.Service.TransactionServiceImpl;
import transactions.models.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TransactionBetweenAccountsTest extends WithApplication {
    private static final String AMOUNT = "2000.60";
    private static final String SENDER_ACC = "533000";
    private static final String RECEIVER_ACC = "324800";
    private static final String CURRENCY = "INR";

    @Mock
    private AccountRepositoryImpl accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void perform() {
        final Transaction build = Transaction.builder()
                .amount(Money.of(new BigDecimal(AMOUNT), CURRENCY))
                .receiverAccountId(Long.decode(RECEIVER_ACC))
                .senderAccountId(Long.decode(SENDER_ACC))
                .timestamp(LocalDateTime.now())
                .transferId(UUID.randomUUID().toString())
                .build();

        when(accountRepository.checkAccountExists(Long.decode(RECEIVER_ACC))).thenReturn(true);
        when(accountRepository.checkAccountExists(Long.decode(SENDER_ACC))).thenReturn(true);
        final Transaction returnedTransfer = transactionService.perform(build);
        assertThat(returnedTransfer.getStatus()).isEqualByComparingTo(TransactionStatus.SUCCESS);
    }
}
