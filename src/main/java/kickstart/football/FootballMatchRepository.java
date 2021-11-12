package kickstart.football;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

interface FootballMatchRepository extends CrudRepository<FootballMatch, Long> {

	@Override
	Streamable<FootballMatch> findAll();
}
