package global.configuration.jooq;

import lombok.NonNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.meta.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    public JooqClient(@NonNull Database database) {
        this.database = database;
        this.jooq = DSL.using(this.database.getConnection(), SQLDialect.H2);
    }

    public DSLContext client() {
        return this.jooq;
    }
}
