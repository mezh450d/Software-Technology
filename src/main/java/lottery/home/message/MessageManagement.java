package lottery.home.message;


import lottery.community.CommunityManagement;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class MessageManagement {

	private final MessageRepository entries;
	private final CommunityManagement communityManagement;

	MessageManagement(MessageRepository messageRepository, CommunityManagement communityManagement) {

		Assert.notNull(messageRepository, "messageRepository must not be null!");

		this.entries = messageRepository;
		this.communityManagement = communityManagement;
	}

	public void save(Message entry){
		entries.save(entry);
		checkTenMessages(entry.getUser());
	}

	public boolean checkTenMessages(String user){
		int messageCount = messageCount(user);
//		if(messageCount >= 10 && !communityManagement.findPersonalCommunities(user).isEmpty()){
//			Message message = new Message(user, "Sie wurden aus der Gemeinschaft entfernt" ,
//					"Sie haben schon 10 Mitteilungen für unzureichende Deckung erhalten",
//					LocalDateTime.now());
//			save(message);
//			for (Community community : communityManagement.findPersonalCommunities(user)) {
//				communityManagement.removeFromCommunity(community, user);
//			}
//		}
		return false;
	}

	public int messageCount(String user){
		return (int)entries.findByUser(user).stream().count();
	}

	public boolean newMessages(String user){
		if((int)entries.findByUserNotRead(user).stream().count() == 0){
			return false;
		} else {
			return true;
		}
	}

	public Streamable<Message> findNotReadByUser(String user){
		return entries.findByUserNotRead(user);
	}

	public Streamable<Message> findReadByUser(String user){
		return entries.findByUserRead(user);
	}

	public Streamable<Message> findEntriesByUser(String user){
		return entries.findByUser(user);
	}

	public Streamable<Message> findAll() { return entries.findAll(); }
}
