package lottery.finance;

import kickstart.football.FootballBet;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

import java.util.Optional;

@EntityScan("bean.FinanceRepository")
interface FinanceRepository extends CrudRepository<FinanceEntry, Long> {

	@Override
	Optional<FinanceEntry> findById(Long aLong);

	@Override
	Streamable<FinanceEntry> findAll();
}
