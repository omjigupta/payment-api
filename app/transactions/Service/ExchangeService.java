package transactions.Service;

import com.google.inject.ImplementedBy;
import global.common.Currency;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.util.HashSet;

/**
 * Exchange service
 *
 * @author omji
 */

@ImplementedBy(ExchangeServiceImpl.class)
public interface ExchangeService {
    boolean checkCurrencyCode(CurrencyUnit currencyUnit);

    HashSet<String> getSupportedCurrencies();

    Money exchange(BigDecimal amount, Currency amountCurrency, Currency targetCurrency);
}
