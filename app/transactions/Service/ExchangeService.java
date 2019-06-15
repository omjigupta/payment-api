package transactions.Service;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.util.HashSet;

/**
 * Exchange service
 *
 * @author omji
 */
public interface ExchangeService {
    boolean checkCurrencyCode(CurrencyUnit currencyUnit);

    Money exchange(Money source, String targetCurrency);

    HashSet<String> getSupportedCurrencies();

    BigDecimal exchangeRate(String sourceCurrency, String targetCurrency);
}
