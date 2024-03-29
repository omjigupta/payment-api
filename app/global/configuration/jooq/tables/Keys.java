package global.configuration.jooq.tables;

import javax.annotation.Generated;

import global.configuration.jooq.records.AccountRecord;
import global.configuration.jooq.records.CustomerRecord;
import global.configuration.jooq.records.TransactionRecord;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of
 * the <code>PUBLIC</code> schema.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.11.11"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<AccountRecord, Long> IDENTITY_ACCOUNT = Identities0.IDENTITY_ACCOUNT;
    public static final Identity<CustomerRecord, Long> IDENTITY_CUSTOMER = Identities0.IDENTITY_CUSTOMER;
    public static final Identity<TransactionRecord, Long> IDENTITY_TRANSACTION = Identities0.IDENTITY_TRANSACTION;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccountRecord> PK_ACCOUNT = UniqueKeys0.PK_ACCOUNT;
    public static final UniqueKey<CustomerRecord> PK_CUSTOMER = UniqueKeys0.PK_CUSTOMER;
    public static final UniqueKey<TransactionRecord> PK_TRANSACTION = UniqueKeys0.PK_TRANSACTION;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<AccountRecord, Long> IDENTITY_ACCOUNT = Internal.createIdentity(Account.ACCOUNT, Account.ACCOUNT.ID);
        public static Identity<CustomerRecord, Long> IDENTITY_CUSTOMER = Internal.createIdentity(Customer.CUSTOMER, Customer.CUSTOMER.ID);
        public static Identity<TransactionRecord, Long> IDENTITY_TRANSACTION = Internal.createIdentity(Transaction.TRANSACTION, Transaction.TRANSACTION.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AccountRecord> PK_ACCOUNT = Internal.createUniqueKey(Account.ACCOUNT, "PK_ACCOUNT", Account.ACCOUNT.ID);
        public static final UniqueKey<CustomerRecord> PK_CUSTOMER = Internal.createUniqueKey(Customer.CUSTOMER, "PK_CUSTOMER", Customer.CUSTOMER.ID);
        public static final UniqueKey<TransactionRecord> PK_TRANSACTION = Internal.createUniqueKey(Transaction.TRANSACTION, "PK_TRANSACTION", Transaction.TRANSACTION.ID);
    }
}
