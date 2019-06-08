package global.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Base model
 */

@Getter
@Setter
public class BaseModel {

    private Long id;
    private long updatedAt, createdAt;

    public BaseModel() {
        updatedAt = createdAt = System.currentTimeMillis() / 1000;
    }

    public enum Fields {updatedAt, createdAt, id}
}
