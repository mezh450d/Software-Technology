package lottery.betting;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

interface BetRepository extends CrudRepository<Bet, Long> {

	@Query("SELECT u FROM Bet u WHERE u.user = ?1")
	Streamable<Bet> findByUser(String user);

	@Query("SELECT u FROM Bet u WHERE u.evaluate = false")
	Streamable<Bet> findNotEvaluatedBets();

	@Override
	Streamable<Bet> findAll();
}
