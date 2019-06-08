package customers.repository;

import com.google.inject.ImplementedBy;

/**
 * Customer repository interface
 *
 * @author omji
 */
@ImplementedBy(CustomerRepositoryImpl.class)
public interface CustomerRepository {
    Long findId(String nationalId);
}
