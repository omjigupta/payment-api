package global.configuration.database;

import com.google.inject.Inject;
import global.configuration.jooq.JooqClient;

import javax.inject.Singleton;

/**
 * Database setup
 * Table creation and data population in database
 * Database cleanup
 *
 * @author omji
 */
@Singleton
public class DatabaseSetup {

    private JooqClient jooq;

    @Inject
    public DatabaseSetup(JooqClient jooq) {
        this.jooq = jooq;
        cleanUpDatabase();
        setupDatabase();
    }

    private void setupDatabase() {
    }


    private void cleanUpDatabase() {
    }
}
