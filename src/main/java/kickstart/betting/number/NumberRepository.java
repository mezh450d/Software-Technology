package kickstart.betting.number;

import kickstart.finance.FinanceEntry;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

@EntityScan("bean.NumberRepository")
interface NumberRepository extends CrudRepository<NumberEntry, Long> {

}
