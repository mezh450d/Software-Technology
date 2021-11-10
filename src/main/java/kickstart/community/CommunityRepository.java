package kickstart.community;

import kickstart.lottery.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;

public interface CommunityRepository extends CrudRepository<Community, Long> {
	@Override
	Streamable<Community> findAll();
}