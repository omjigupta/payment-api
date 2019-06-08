package bankaccounts.models;


import global.common.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Account model
 * @see BaseModel
 *
 * @author omji
 */

@Data
public class Account extends BaseModel implements Serializable {

    private Double accountNumber;
    private BigDecimal balance;
    private String currency;

}
