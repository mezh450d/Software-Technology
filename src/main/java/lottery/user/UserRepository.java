package lottery.user;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

	@Override
	Streamable<User> findAll();
	@Override
	Optional<User> findById(Long aLong);
}
