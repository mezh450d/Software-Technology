package lottery.betting.number;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

@EntityScan("bean.NumberRepository")
interface NumberRepository extends CrudRepository<LotteryEntity, Long> {

}
