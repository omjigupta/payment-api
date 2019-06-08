package customers.repository;

import com.google.inject.Inject;
import global.configuration.jooq.JooqClient;
import org.jooq.Record1;


import static global.configuration.jooq.tables.Customer.CUSTOMER;

/**
 * Customer repository implementation
 * @see customers.repository.CustomerRepository
 * @author omji
 */
public class CustomerRepositoryImpl implements CustomerRepository {

    private JooqClient jooq;

    @Inject
    public CustomerRepositoryImpl(JooqClient jooq) {
        this.jooq = jooq;
    }

    @Override
    public Long findId(String customerGovtId) {
        final Record1<Long> id = jooq.client()
                .select(CUSTOMER.ID)
                .from(CUSTOMER)
                .where(CUSTOMER.CUSTOMER_GOVT_ID.equal(customerGovtId))
                .fetchOne();

        return id.value1();
    }
}
