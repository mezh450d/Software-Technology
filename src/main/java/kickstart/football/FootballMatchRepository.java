package kickstart.football;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

interface FootballMatchRepository extends CrudRepository<FootballMatch, Long> {

	@Override
	Optional<FootballMatch> findById(Long aLong);

	@Override
	Streamable<FootballMatch> findAll();
}
