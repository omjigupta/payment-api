package transactions.Service;

import global.common.Currency;
import global.common.CurrencyUtil;
import javafx.util.Pair;
import lombok.NonNull;
import org.javamoney.moneta.Money;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Exchange service implementation
 * @see ExchangeService
 * @author omji
 */
@Singleton
public class ExchangeServiceImpl implements ExchangeService {

    /**
     * Exchange took from
     * https://www.xe.com/currencyconverter/convert/?Amount=1&From=USD&To=INR
     * Date - 2019-06-15 12:05 UTC
     */
    private final static Map<Pair<Currency, Currency>, BigDecimal> exchangeRates =
            Collections.unmodifiableMap(new HashMap<Pair<Currency, Currency>, BigDecimal>() {{
                put(new Pair<>(Currency.INR, Currency.EUR), BigDecimal.valueOf(0.013));
                put(new Pair<>(Currency.INR, Currency.USD), BigDecimal.valueOf(0.014));
                put(new Pair<>(Currency.INR, Currency.INR), BigDecimal.valueOf(1D));
                put(new Pair<>(Currency.USD, Currency.EUR), BigDecimal.valueOf(0.89));
                put(new Pair<>(Currency.USD, Currency.USD), BigDecimal.valueOf(1D));
                put(new Pair<>(Currency.USD, Currency.INR), BigDecimal.valueOf(69.86));
                put(new Pair<>(Currency.EUR, Currency.EUR), BigDecimal.valueOf(1D));
                put(new Pair<>(Currency.EUR, Currency.USD), BigDecimal.valueOf(1.12));
                put(new Pair<>(Currency.EUR, Currency.INR), BigDecimal.valueOf(78.35));
            }});

    @Override
    public boolean checkCurrencyCode(@NonNull CurrencyUnit currencyUnit) {
        return CurrencyUtil.getCurrencySet().contains(currencyUnit.getCurrencyCode());
    }

    @Override
    public Money exchange(Money source, String targetCurrency) {
        return null;
    }

    @Override
    public HashSet<String> getSupportedCurrencies() {
        return CurrencyUtil.getCurrencySet();
    }

    @Override
    public BigDecimal exchangeRate(String sourceCurrency, String targetCurrency) {
        return null;
    }
}
