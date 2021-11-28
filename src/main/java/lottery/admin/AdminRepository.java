package lottery.admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<UserInfo, Long> {

	@Override
	Optional<UserInfo> findById(Long aLong);

	@Override
	Streamable<UserInfo> findAll();
}
