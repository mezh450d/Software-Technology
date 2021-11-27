package lottery.finance;

import lottery.betting.Bet;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

@EntityScan("bean.FinanceRepository")
public interface FinanceRepository extends CrudRepository<FinanceEntry, Long> {

	@Override
	Optional<FinanceEntry> findById(Long aLong);

	@Override
	Streamable<FinanceEntry> findAll();

	@Query("SELECT u FROM FinanceEntry u WHERE u.user = ?1")
	Streamable<FinanceEntry> findByUser(String user);

}
