package global.configuration.database;

import com.google.inject.Inject;
import global.common.Currency;
import global.configuration.jooq.JooqClient;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.SQLDataType;
import play.Logger;

import javax.inject.Singleton;

import java.math.BigDecimal;
import java.util.List;

import static global.configuration.jooq.tables.Tables.CUSTOMER;
import static global.configuration.jooq.tables.Account.ACCOUNT;
import static org.jooq.impl.DSL.constraint;

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
        createTables();
        populateTables();

        final Result<Record> customers = jooq.client().select().from(getTable("customer")).fetch();
        final Result<Record> account = jooq.client().select().from(getTable("account")).fetch();

        Logger.info(() -> "In-Memory Bank Customers: \n" + customers.toString());
        Logger.info(() -> "In-Memory Bank Accounts: \n" + account.toString());
    }

    private void createTables() {
        jooq.client()
                .createTableIfNotExists(CUSTOMER)
                .column(CUSTOMER.ID, SQLDataType.BIGINT.identity(true))
                .column(CUSTOMER.FIRST_NAME, SQLDataType.VARCHAR.length(64).nullable(false))
                .column(CUSTOMER.LAST_NAME, SQLDataType.VARCHAR.length(64).nullable(false))
                .constraints(
                        constraint("PK_CUSTOMER").primaryKey("id")
                )
                .execute();

        jooq.client()
                .createTableIfNotExists(ACCOUNT)
                .column(ACCOUNT.ID, SQLDataType.BIGINT.identity(true))
                .column(ACCOUNT.ACCOUNT_NUMBER, SQLDataType.BIGINT.length(9).nullable(false))
                .column(ACCOUNT.CUSTOMER_ID, SQLDataType.BIGINT)
                .column(ACCOUNT.BALANCE, SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(new BigDecimal(0)))
                .column(ACCOUNT.CURRENCY, SQLDataType.VARCHAR(3).nullable(false))
                .constraints(
                        constraint("PK_ACCOUNT").primaryKey("id")
                )
                .execute();
    }

    private void populateTables() {
        jooq.client()
                .insertInto(CUSTOMER, CUSTOMER.ID, CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME)
                .values(1001L, "Om", "Ji")
                .values(2001L, "Suvesh", "Kumar")
                .execute();

        jooq.client()
                .insertInto(ACCOUNT, ACCOUNT.ID, ACCOUNT.ACCOUNT_NUMBER, ACCOUNT.CUSTOMER_ID, ACCOUNT.BALANCE, ACCOUNT.CURRENCY)
                .values(100L, 533000L, 1001L, new BigDecimal(5000.50), Currency.INR.name())
                .values(200L, 124800L, 2001L, new BigDecimal(10), Currency.EUR.name())
                .execute();
    }


    private void cleanUpDatabase() {
    }

    private Table<?> getTable(String tableName) {
        final List<Table<?>> tableCandidate = jooq.client().meta().getTables(tableName);
        if (!tableCandidate.isEmpty() && tableCandidate.get(0) != null) {
            return tableCandidate.get(0);
        }
        return null;
    }
}
