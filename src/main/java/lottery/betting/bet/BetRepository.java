package lottery.betting.bet;

import lottery.betting.data.Category;
import lottery.betting.data.Data;
import lottery.betting.data.Result;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

public interface BetRepository extends CrudRepository<Bet, Long> {

	@Query("SELECT u FROM Bet u WHERE u.origin = ?1 AND u.evaluate = false")
	Streamable<Bet> findBetsByOrigin(String user);

	@Query("SELECT u FROM Bet u WHERE u.type = lottery.betting.bet.Type.COMMUNITY " +
			"AND u.origin = ?1 AND u.reference.category = ?2 AND u.evaluate = false")
	Streamable<CommunityBet> findCommunityBetsByCommunityAndCategory(String community, Category category);

	@Query("SELECT u FROM Bet u WHERE u.type = ?1 AND u.evaluate = false")
	Streamable<Data> findByType(Type type);

	@Query("SELECT u FROM Bet u WHERE u.evaluate = false")
	Streamable<Bet> findNotEvaluatedBets();

	@Query("SELECT u FROM Bet u WHERE u.reference = ?1 AND u.evaluate = false")
	Streamable<Bet> findByData(Data data);

	@Query("SELECT u FROM Bet u WHERE u.type = lottery.betting.bet.Type.COMMUNITY AND u.id = ?1 AND u.evaluate = false")
	CommunityBet findCommunityBetById(long id);

	@Override
	Streamable<Bet> findAll();
}
