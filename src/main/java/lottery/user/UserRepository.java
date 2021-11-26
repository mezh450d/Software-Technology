package lottery.user;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

	@Override
	Optional<User> findById(Long id);

	@Override
	Streamable<User> findAll();
}
