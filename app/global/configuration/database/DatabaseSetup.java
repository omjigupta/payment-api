package global.configuration.database;

import global.configuration.jooq.JooqClient;

import javax.inject.Inject;
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
