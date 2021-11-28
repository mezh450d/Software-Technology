package lottery.betting;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

public interface BetRepository extends CrudRepository<Bet, Long> {

	@Query("SELECT u FROM Bet u WHERE u.user = ?1")
	Streamable<Bet> findByUser(String user);

	@Query("SELECT u FROM Bet u WHERE u.evaluate = false")
	Streamable<Bet> findNotEvaluatedBets();

	@Query("SELECT u FROM Bet u WHERE u.reference = ?1")
	Streamable<Bet> findByData(Data data);

	@Override
	Streamable<Bet> findAll();
}
