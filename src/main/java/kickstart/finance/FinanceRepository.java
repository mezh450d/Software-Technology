package kickstart.finance;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

@EntityScan("bean.FinanceRepository")
interface FinanceRepository extends CrudRepository<FinanceEntry, Long> {


	Streamable<FinanceEntry> findByAmount(Double amount, Sort sort);
}
