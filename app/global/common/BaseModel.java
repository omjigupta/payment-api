package global.common;

import io.ebean.Model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class BaseModel extends Model {

    @Id
    private Long id;
    private long updatedAt, createdAt;

    public enum Fields {updatedAt, createdAt, id}
}
