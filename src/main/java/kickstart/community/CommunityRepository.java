package kickstart.community;

import kickstart.lottery.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends CrudRepository<Community, Long> {
	@Override
	Streamable<Community> findAll();
//	Streamable<Community> findByCreateForm(CreateForm createForm, Sort sort);
}