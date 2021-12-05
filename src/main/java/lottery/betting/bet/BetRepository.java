package lottery.betting.bet;

import lottery.betting.data.Data;
import lottery.betting.data.Result;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

public interface BetRepository extends CrudRepository<Bet, Long> {

	@Query("SELECT u FROM Bet u WHERE u.type = lottery.betting.bet.Type.INDIVIDUAL " +
			"AND u.origin = ?1 AND u.evaluate = false")
	Streamable<Bet> findIndividualByUser(String user);

	@Query("SELECT u FROM Bet u WHERE u.type = lottery.betting.bet.Type.COMMUNITY " +
			"AND u.origin = ?1 AND u.evaluate = false")
	Streamable<Bet> findCommunityBetsByCommunity(String community);

	@Query("SELECT u FROM Bet u WHERE u.type = lottery.betting.bet.Type.COMMUNITY " +
			"AND u.origin = ?1 AND u.reference = ?2 AND u.value = ?3 AND u.evaluate = false")
	Optional<CommunityBet> equalCommunityBet(String origin, Data reference, Result value);

	@Query("SELECT u FROM Bet u WHERE u.type = ?1 AND u.evaluate = false")
	Streamable<Data> findByType(Type type);

	@Query("SELECT u FROM Bet u WHERE u.evaluate = false")
	Streamable<Bet> findNotEvaluatedBets();

	@Query("SELECT u FROM Bet u WHERE u.reference = ?1 AND u.evaluate = false")
	Streamable<Bet> findByData(Data data);

	@Override
	Streamable<Bet> findAll();
}
