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
import static global.configuration.jooq.tables.Tables.TRANSACTION;
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
                .column(CUSTOMER.CUSTOMER_GOVT_ID, SQLDataType.VARCHAR.length(12).nullable(false))
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

        jooq.client()
                .createTableIfNotExists(TRANSACTION)
                .column(TRANSACTION.ID, SQLDataType.BIGINT.identity(true))
                .column(TRANSACTION.SENDER_ACCOUNT, SQLDataType.BIGINT)
                .column(TRANSACTION.RECEIVER_ACCOUNT, SQLDataType.BIGINT)
                .column(TRANSACTION.AMOUNT, SQLDataType.DECIMAL(10, 2).nullable(false))
                .column(TRANSACTION.CURRENCY, SQLDataType.VARCHAR(3).nullable(false))
                .column(TRANSACTION.DATE_TIME, SQLDataType.TIMESTAMP(1))
                .column(TRANSACTION.TRANSACTION_ID, SQLDataType.VARCHAR(50).nullable(false))
                .constraints(
                        constraint("PK_TRANSACTION").primaryKey("id")
                )
                .execute();
    }

    private void populateTables() {
        jooq.client()
                .insertInto(CUSTOMER, CUSTOMER.ID, CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME, CUSTOMER.CUSTOMER_GOVT_ID)
                .values(1001L, "Om", "Ji", "OMJI101")
                .values(2001L, "Suvesh", "Kumar", "SUVI201")
                .values(3001L, "Ankit", "Agnihotri", "ANKT301")
                .values(4001L, "Ashwani", "Kumar", "ASH401")
                .execute();

        jooq.client()
                .insertInto(ACCOUNT, ACCOUNT.ID, ACCOUNT.ACCOUNT_NUMBER, ACCOUNT.CUSTOMER_ID, ACCOUNT.BALANCE, ACCOUNT.CURRENCY)
                .values(100L, 533000L, 1001L, new BigDecimal(5000.50), Currency.INR.name())
                .values(200L, 124800L, 2001L, new BigDecimal(10), Currency.EUR.name())
                .values(300L, 324800L, 3001L, new BigDecimal(1000), Currency.INR.name())
                .values(400L, 424800L, 4001L, new BigDecimal(13240), Currency.USD.name())
                .execute();
    }


    private void cleanUpDatabase() {
        jooq.client().dropTableIfExists(ACCOUNT).execute();
        jooq.client().dropTableIfExists(CUSTOMER).execute();
        jooq.client().dropTableIfExists(TRANSACTION).execute();
    }

    private Table<?> getTable(String tableName) {
        final List<Table<?>> tableCandidate = jooq.client().meta().getTables(tableName);
        if (!tableCandidate.isEmpty() && tableCandidate.get(0) != null) {
            return tableCandidate.get(0);
        }
        return null;
    }
}
