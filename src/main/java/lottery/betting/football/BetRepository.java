package lottery.betting.football;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

interface BetRepository extends CrudRepository<FootballBet, Long> {

	@Override
	Optional<FootballBet> findById(Long aLong);

	@Override
	Streamable<FootballBet> findAll();
}
