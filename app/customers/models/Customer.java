package customers.models;

import global.common.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * Customer Model
 * @see BaseModel
 *
 * @author omji
 */
@Data
public class Customer extends BaseModel implements Serializable {

    private String firstName;
    private String lastName;
}
