package lottery.admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<AdminEntry, Long> {

	@Override
	Optional<AdminEntry> findById(Long id);

	@Override
	Streamable<AdminEntry> findAll();

	@Override
	Iterable<AdminEntry> findAllById(Iterable<Long> ids);
}