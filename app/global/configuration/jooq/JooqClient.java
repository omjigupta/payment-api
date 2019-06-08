package global.configuration.jooq;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import play.api.db.Database;


/**
 * Jooq database client
 *
 * @author omji
 */
@Singleton
public class JooqClient {

    private Database database;
    private DSLContext jooq;

    @Inject
    public JooqClient(Database database) {
        this.database = database;
        this.jooq = DSL.using(this.database.getConnection(), SQLDialect.H2);
    }

    public DSLContext client() {
        return this.jooq;
    }
}
