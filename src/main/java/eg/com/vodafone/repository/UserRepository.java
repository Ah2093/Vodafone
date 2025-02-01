package eg.com.vodafone.repository;

import eg.com.vodafone.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserName(String userName);


}
