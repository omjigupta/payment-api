package global.common;

import java.util.HashSet;

/**
 * Currency Utility class which will provide list of supported currency
 *
 * @author omji
 */
public class CurrencyUtil {

    private static HashSet<String> values = new HashSet<String>();

    public static HashSet<String> getCurrencySet() {

        for (Currency c : Currency.values()) {
            values.add(c.name());
        }

        return values;
    }
}
